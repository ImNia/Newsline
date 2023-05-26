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

    fun initData() {
        getData()
    }

    private fun getData() {
        request.news("CVPxVQdA6BvGZQkycPAM9uG1CEk6pjsF").enqueue(
            object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.isSuccessful) {
                        Log.d("REQUEST_MODEL", (response.body() as News).toString())
                    }
                    newsLiveData.value = response.body() as News
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.d("REQUEST_MODEL", "${t.printStackTrace()}")
                }

            }
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}