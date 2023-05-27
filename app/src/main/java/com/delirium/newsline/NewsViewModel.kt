package com.delirium.newsline;

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delirium.newsline.model.News
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
        dataReceived.value = DataState.LOAD
        request.news("CVPxVQdA6BvGZQkycPAM9uG1CEk6pjsF").enqueue(
            object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.isSuccessful) {
                        dataReceived.value = DataState.RECEIVED
                        newsLiveData.value = response.body() as News
                        Log.d("REQUEST_MODEL", (response.body() as News).toString())
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.d("REQUEST_MODEL", "Fail: ${t.printStackTrace()}")
                    dataReceived.value = DataState.ERROR
                }

            }
        )
    }
}