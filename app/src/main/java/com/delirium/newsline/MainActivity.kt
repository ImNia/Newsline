package com.delirium.newsline

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.newsline.model.NewsItem
import com.delirium.newsline.recycler.AdapterRecycler
import com.delirium.newsline.recycler.ClickListener

class MainActivity : AppCompatActivity(), ClickListener {
    private lateinit var viewModel: NewsViewModel
    private lateinit var recycler: RecyclerView
    private val adapter = AdapterRecycler(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var buttonRetry: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.newsLiveData.observe(this) {
            Log.d("TEST_MAIN", "GetData: $it")
            adapter.news = it.results
            adapter.notifyDataSetChanged()
        }

        viewModel.dataReceived.observe(this) {
            when (it) {
                DataState.LOAD -> {
                    progressBar.visibility = View.VISIBLE
                    recycler.visibility = View.INVISIBLE
                }
                DataState.RECEIVED -> {
                    progressBar.visibility = View.INVISIBLE
                    recycler.visibility = View.VISIBLE
                }
                DataState.ERROR -> {
                    setContentView(R.layout.error_item)
                    buttonRetry = findViewById(R.id.button_retry)
                    buttonRetry.setOnClickListener {
                        loadData()
                    }
                }
                else -> {
                    Log.e("MAIN_ACTIVITY", "Something wrong")
                }
            }
        }

        loadData()
    }

    private fun loadData() {
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress_bar)
        viewModel.loadData()
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }

    override fun clickOpenWebSite(item: NewsItem) {
        val address = Uri.parse(item.url)
        Intent(Intent.ACTION_VIEW, address).apply {
            startActivity(this)
        }
    }
}