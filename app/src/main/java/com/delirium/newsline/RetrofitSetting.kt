package com.delirium.newsline

import com.delirium.newsline.model.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitSetting {
    //https://api.nytimes.com/svc/topstories/v2/world.json?api-key=CVPxVQdA6BvGZQkycPAM9uG1CEk6pjsF
    private const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
    val newsRequest: NewsRequest
        get() = RetrofitClient.getClient(BASE_URL).create(NewsRequest::class.java)
}

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}

interface NewsRequest {
    @GET("world.json")
    fun news(
        @Query("api-key") key: String
    ): Call<News>
}