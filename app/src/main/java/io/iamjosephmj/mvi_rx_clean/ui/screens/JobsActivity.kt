/*
* MIT License
*
* Copyright (c) 2021 Joseph James
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*
*/

package io.iamjosephmj.mvi_rx_clean.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willowtreeapps.spruce.Spruce.SpruceBuilder
import com.willowtreeapps.spruce.animation.DefaultAnimations
import com.willowtreeapps.spruce.sort.LinearSort
import io.iamjosephmj.core.domain.SearchRequest
import io.iamjosephmj.mvi_rx_clean.R
import io.iamjosephmj.mvi_rx_clean.di.component.ActivityComponent
import io.iamjosephmj.mvi_rx_clean.ui.base.BaseActivity
import io.iamjosephmj.mvi_rx_clean.ui.screens.adapter.jobsAdapter
import io.iamjosephmj.mvi_rx_clean.ui.viewmodels.JobsViewModel
import io.iamjosephmj.presentation.mvi.intents.GitHubLoadJobsIntent
import io.iamjosephmj.presentation.mvi.mvibase.MVIView
import io.iamjosephmj.presentation.mvi.viewstate.GitHubJobsViewState
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*


/**
 * This is the Activity that displays the list of jobs.
 */
class JobsActivity : BaseActivity<JobsViewModel>(), MVIView<GitHubLoadJobsIntent, GitHubJobsViewState> {
    //TODO : You can add a clear button and call the clear all intent to see the UI state change.
    lateinit var animationBuilder: SpruceBuilder

    var animateList = true

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        initRecyclerView()
        initSpruce()
        initSwipeRefresh()
        initViewModelRxBindings()
        startApiCall()
    }

    private fun initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            animateList = true
            loadJobsIntent.onNext(
                GitHubLoadJobsIntent.GitHubLoadWithData( SearchRequest(
                    domain = "Android",
                    page = 1
                )))
        }
    }

    private fun startApiCall() {
        loadJobsIntent.onNext(
            GitHubLoadJobsIntent.GitHubLoadWithData(
                SearchRequest(
                    domain = "Android",
                    page = 1
                )
            )
        )
    }

    /**
     * This methods binds the intent objects with the viewModel
     */
    private fun initViewModelRxBindings() {
        compositeDisposable.add(
            viewModel.states().subscribe(this::render)
        )
        viewModel.processIntent(intents())
    }

    /**
     * This module is used to initialize the recycler view starting animations.
     */
    private fun initSpruce() {
        animationBuilder =
            SpruceBuilder(jobsRecyclerView)
                .sortWith(
                    LinearSort( /*interObjectDelay=*/100L,  /*reversed=*/
                        false,
                        LinearSort.Direction.TOP_TO_BOTTOM
                    )
                )
                .animateWith(
                    DefaultAnimations.dynamicTranslationUpwards(jobsRecyclerView),
                    DefaultAnimations.dynamicFadeIn(jobsRecyclerView)
                )
    }

    /**
     * This method is used to initialize recycler view.
     */
    private fun initRecyclerView() {
        jobsRecyclerView.layoutManager = object : LinearLayoutManager(this) {
            override fun onLayoutChildren(
                recycler: RecyclerView.Recycler,
                state: RecyclerView.State
            ) {
                super.onLayoutChildren(recycler, state)
                if (jobsRecyclerView.childCount > 0 && animateList) {
                    animationBuilder.start()
                    animateList = false
                }
            }
        }
        jobsRecyclerView.adapter = jobsAdapter
    }

    override fun intents(): Observable<GitHubLoadJobsIntent> {
        return Observable.merge(
            loadJobsIntent,
            clearJobsIntent
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun render(state: GitHubJobsViewState) {
        when {
            state.isLoading -> {
                if(!swipeRefreshLayout.isRefreshing) {
                    loadingView.visibility = View.VISIBLE
                    jobsRecyclerView.visibility = View.INVISIBLE
                    errorView.visibility = View.INVISIBLE
                }
            }
            state.error != null -> {
                errorView.frame = 0
                loadingView.visibility = View.INVISIBLE
                jobsRecyclerView.visibility = View.INVISIBLE
                errorView.visibility = View.VISIBLE
                errorView.playAnimation()
            }
            else -> {
                loadingView.visibility = View.INVISIBLE
                jobsRecyclerView.visibility = View.VISIBLE
                errorView.visibility = View.INVISIBLE
                jobsAdapter.items = state.jobList
                jobsAdapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private var loadJobsIntent = PublishSubject.create<GitHubLoadJobsIntent.GitHubLoadWithData>()

    private val clearJobsIntent = PublishSubject.create<GitHubLoadJobsIntent.ClearAllJobsGitHub>()

}