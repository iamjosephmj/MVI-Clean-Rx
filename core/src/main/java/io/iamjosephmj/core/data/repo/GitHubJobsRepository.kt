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
package io.iamjosephmj.core.data.repo

import io.iamjosephmj.core.data.datasource.GitHubJobsDataSource
import io.iamjosephmj.core.domain.GitHubJobDescription
import io.iamjosephmj.core.domain.SearchRequest
import io.reactivex.Single

/**
 *  Provides methods for accessing the data that delegate to {@link GitHubJobsDataSource}
 *
 * @author Joseph James
 */
class GitHubJobsRepository(private val gitHubJobsDataSource: GitHubJobsDataSource) {
    /**
     * Refer to the data source implementation.
     */
    fun searchJobs(searchRequest: SearchRequest): Single<List<GitHubJobDescription>> {
        return gitHubJobsDataSource.searchJobs(searchRequest)
    }
}