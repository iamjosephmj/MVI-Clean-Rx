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

package io.iamjosephmj.mvi_rx_clean.di.module

import dagger.Module
import dagger.Provides
import io.iamjosephmj.presentation.util.SchedulerProvider
import io.iamjosephmj.core.interactors.Interactors
import io.iamjosephmj.presentation.mvi.intents.GitHubLoadJobsIntent
import io.iamjosephmj.presentation.mvi.processor.GitHubJobsProcessorHolder
import io.reactivex.subjects.PublishSubject

/**
 * @author Joseph James
 */
@Module
class ViewModelModule {

    @Provides
    fun providesIntentSubjects(): PublishSubject<GitHubLoadJobsIntent> {
        return PublishSubject.create()
    }

    @Provides
    fun providesGitHubJobsProcessorHolder(
        interactors: Interactors,
        schedulerProvider: SchedulerProvider
    ): GitHubJobsProcessorHolder {
        return GitHubJobsProcessorHolder(interactors, schedulerProvider)
    }
}