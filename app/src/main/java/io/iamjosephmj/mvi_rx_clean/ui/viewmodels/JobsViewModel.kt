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

package io.iamjosephmj.mvi_rx_clean.ui.viewmodels

import io.iamjosephmj.mvi_rx_clean.di.component.ViewModelComponent
import io.iamjosephmj.mvi_rx_clean.ui.base.BaseViewModel
import io.iamjosephmj.presentation.mvi.action.GithubLoadJobsAction
import io.iamjosephmj.presentation.mvi.intents.GitHubLoadJobsIntent
import io.iamjosephmj.presentation.mvi.mvibase.MVIViewModel
import io.iamjosephmj.presentation.mvi.processor.GitHubJobsProcessorHolder
import io.iamjosephmj.presentation.mvi.results.GitHubJobsResult
import io.iamjosephmj.presentation.mvi.viewstate.GitHubJobsViewState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * This ViewModel is specifically used for implementing the jobsFetch-display operations in this project.
 */
class JobsViewModel : BaseViewModel(), MVIViewModel<GitHubLoadJobsIntent, GitHubJobsViewState> {

    @Inject
    lateinit var actionProcessorHolder: GitHubJobsProcessorHolder

    @Inject
    lateinit var intentSubjectsGitHub: PublishSubject<GitHubLoadJobsIntent>

    private val stateObservable: Observable<GitHubJobsViewState> = compose()

    private fun compose(): Observable<GitHubJobsViewState> {
        return intentSubjectsGitHub
//            .compose(intentFilter)
            .map(this::actionFromIntent)
            .compose(actionProcessorHolder.actionProcessor)
            .scan(GitHubJobsViewState.default(), reducer)
            .distinctUntilChanged()
            .replay(1)
            .autoConnect(0)
    }

    private val intentFilterGitHub: ObservableTransformer<GitHubLoadJobsIntent, GitHubLoadJobsIntent>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    shared.ofType(GitHubLoadJobsIntent.GitHubLoadWithData::class.java).take(1),
                    shared.ofType(GitHubLoadJobsIntent.ClearAllJobsGitHub::class.java).take(1)
                )

            }
        }

    private fun actionFromIntent(intentGitHub: GitHubLoadJobsIntent): GithubLoadJobsAction {
        return when (intentGitHub) {
            is GitHubLoadJobsIntent.GitHubLoadWithData -> GithubLoadJobsAction.GithubLoadJobs(intentGitHub.searchRequest)
            is GitHubLoadJobsIntent.ClearAllJobsGitHub -> GithubLoadJobsAction.ClearAllJobsGithub
        }
    }

    override fun injectDependencies(viewModelComponent: ViewModelComponent) {
        viewModelComponent.inject(this)
    }

    override fun onStart() {
    }

    override fun processIntent(intentGitHub: Observable<GitHubLoadJobsIntent>) {
        intentGitHub.subscribe(intentSubjectsGitHub)

    }

    override fun states(): Observable<GitHubJobsViewState> = stateObservable

    companion object {
        private val reducer =
            BiFunction { previousViewState: GitHubJobsViewState, result: GitHubJobsResult ->
                when (result) {
                    is GitHubJobsResult.LoadAllJobs.Loading ->
                        previousViewState.copy(
                            isLoading = true,
                            jobList = ArrayList(),
                            error = null
                        )
                    is GitHubJobsResult.LoadAllJobs.Error ->
                        previousViewState.copy(
                            isLoading = false,
                            error = result.error
                        )
                    is GitHubJobsResult.LoadAllJobs.Success ->
                        previousViewState.copy(
                            isLoading = false,
                            error = null,
                            jobList = result.jobsList
                        )
                }
            }
    }

}