package com.delirium.newsline;

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delirium.newsline.model.News
import com.delirium.newsline.model.NewsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel: ViewModel() {
    private val request = RetrofitSetting.newsRequest
    val newsLiveData = MutableLiveData<News>()
    val dataReceived = MutableLiveData<DataState>()

    fun loadData() {
        getData()
    }

    private fun getData() {
        dataReceived.postValue(DataState.LOAD)
        request.news("CVPxVQdA6BvGZQkycPAM9uG1CEk6pjsF").enqueue(
            object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.isSuccessful) {
                        dataReceived.postValue(DataState.RECEIVED)
                        prepareDate(response.body() as News)
                        Log.d("REQUEST_MODEL", (response.body() as News).toString())
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.d("REQUEST_MODEL", "Fail: ${t.printStackTrace()}")
                    dataReceived.postValue(DataState.ERROR)
                }

            }
        )
    }

    private fun prepareDate(data: News) {
        val prepareNews = mutableListOf<NewsItem>()
        data.results.forEach {
            if (it.url.isNotEmpty()) {
                prepareNews.add(it)
            }
        }
//        newsLiveData.value = News(prepareNews)
        newsLiveData.postValue(News(prepareNews))
    }
}