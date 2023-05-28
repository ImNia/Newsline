package com.delirium.newsline

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.newsline.model.NewsItem
import com.delirium.newsline.recycler.AdapterRecycler
import com.delirium.newsline.recycler.ClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ClickListener {
    private val viewModel by viewModel<NewsViewModel>()
    private lateinit var recycler: RecyclerView
    private val adapter = AdapterRecycler(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var buttonRetry: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.update -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateData() {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.loadData()
        }
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