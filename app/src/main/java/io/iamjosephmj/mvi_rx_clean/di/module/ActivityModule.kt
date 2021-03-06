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

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.iamjosephmj.mvi_rx_clean.di.ActivityScope
import io.iamjosephmj.mvi_rx_clean.ui.base.BaseActivity
import io.iamjosephmj.mvi_rx_clean.ui.viewmodels.JobsViewModel

/**
 * @author Joseph James
 */
@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @ActivityScope
    @Provides
    fun providesJobsViewModel(): JobsViewModel {
        return ViewModelProviders.of(activity).get(JobsViewModel::class.java)
    }

}