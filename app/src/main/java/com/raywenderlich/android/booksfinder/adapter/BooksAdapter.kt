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

package com.raywenderlich.android.booksfinder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.booksfinder.R
import com.raywenderlich.android.booksfinder.model.Book
import com.squareup.picasso.Picasso

class BooksAdapter(private val books: List<Book>) :
  RecyclerView.Adapter<BooksAdapter.BooksHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
    return BooksHolder(view)
  }

  override fun onBindViewHolder(holder: BooksHolder, position: Int) {
    val book = books[position]
    holder.bind(book)
  }

  override fun getItemCount(): Int = books.count()

  class BooksHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(book: Book) {
      val ivThumbnail = itemView.findViewById<ImageView>(R.id.ivThumbnail)
      if (book.coverId != null) {
        val imageUrl = "http://covers.openlibrary.org/b/id/${book.coverId}-S.jpg"
        Picasso.get().load(imageUrl).into(ivThumbnail)
      } else {
        ivThumbnail.setImageResource(R.drawable.ic_books)
      }
      val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
      tvTitle.text = book.title
      if (book.authors != null && book.authors.count() > 0) {
        val tvAuthor = itemView.findViewById<TextView>(R.id.tvAuthor)
        tvAuthor.text = book.authors[0]
      }
    }
  }
}
