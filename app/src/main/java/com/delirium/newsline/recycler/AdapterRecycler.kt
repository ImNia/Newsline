package com.delirium.newsline.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delirium.newsline.R
import com.delirium.newsline.model.NewsItem

class AdapterRecycler(
    private val clickListener: ClickListener
) : RecyclerView.Adapter<ViewHolder>() {
    var news: List<NewsItem> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view, clickListener)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount() = news.size

}

interface ClickListener {
    fun clickOpenWebSite(item: NewsItem)
}