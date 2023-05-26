package com.delirium.newsline.model

data class NewsItem(
    var title: String,
    var url: String,
    var published_date: String,
    var multimedia: List<Multimedia>
)
