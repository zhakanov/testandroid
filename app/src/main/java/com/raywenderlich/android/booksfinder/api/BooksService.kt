/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.booksfinder.api

import com.google.gson.GsonBuilder
import com.raywenderlich.android.booksfinder.model.Book
import com.raywenderlich.android.booksfinder.model.BooksResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BooksService {
  private const val BASE_URL = "http://openlibrary.org"

  fun getBooks(query: String, completion: (List<Book>?, String?) -> Unit) {
    val retrofit = createRetrofit()
    val booksAPI = retrofit.create(BooksAPI::class.java)
    val result = booksAPI.getBooks(query)
    result.enqueue(object : Callback<BooksResult> {
      override fun onResponse(call: Call<BooksResult>, response: Response<BooksResult>) {
        if (response.isSuccessful) {
          val booksResponse = response.body()
          completion(booksResponse?.books, null)
        } else {
          completion(null, response.errorBody()?.string())
        }
      }

      override fun onFailure(call: Call<BooksResult>?, t: Throwable?) {
        completion(null, t.toString())
      }
    })
  }

  private fun createRetrofit(): Retrofit {
    val gson = GsonBuilder()
      .create()
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
  }
}
