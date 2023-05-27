package com.delirium.newsline.recycler

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.newsline.R
import com.delirium.newsline.model.NewsItem
import java.text.SimpleDateFormat
import java.util.*

class ViewHolder(itemView: View, private val clickListener: ClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val imageNews: ImageView
    private val titleNews: TextView
    private val publishedDate: TextView
    private val buttonOpenNews: Button

    init {
        imageNews = itemView.findViewById(R.id.image)
        titleNews = itemView.findViewById(R.id.title)
        publishedDate = itemView.findViewById(R.id.date_published)
        buttonOpenNews = itemView.findViewById(R.id.button_open)
    }

    fun bind(data: NewsItem) {
        Glide.with(itemView)
            .load(data.multimedia.first().url)
            .placeholder(R.drawable.default_image)
            .transform(RoundedCorners(4))
            .into(imageNews)
        titleNews.text = if(data.title.isEmpty()) {
            "Not Title"
        } else {
            data.title
        }
        publishedDate.text = formattedDate(data.published_date)

        buttonOpenNews.setOnClickListener {
            clickListener.clickOpenWebSite(data)
        }
    }

    private fun formattedDate(date: String): String {
        var format = SimpleDateFormat("yyyy-MM-dd")
        val newDate = format.parse(date)
        format = SimpleDateFormat("dd-MM-yyyy")
        val date = format.format(newDate)
        return "Publish date: $date"
    }
}