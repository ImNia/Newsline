package com.delirium.newsline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.newsline.model.News
import com.delirium.newsline.model.NewsItem
import com.delirium.newsline.recycler.AdapterRecycler
import com.delirium.newsline.recycler.ClickListener

class MainActivity : AppCompatActivity(), ClickListener {
    private lateinit var viewModel: NewsViewModel
    private lateinit var recycler: RecyclerView
    private val adapter = AdapterRecycler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.newsLiveData.observe(this, Observer {
            Log.d("TEST_MAIN", "GetData: $it")
            adapter.news = it.results
            adapter.notifyDataSetChanged()
        })

        viewModel.initData()

        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

    }

    override fun clickOpenWebSite(item: NewsItem) {
        TODO("Not yet implemented")
    }

}