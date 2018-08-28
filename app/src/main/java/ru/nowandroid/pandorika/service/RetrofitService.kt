package ru.nowandroid.pandorika.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import kotlinx.coroutines.experimental.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nowandroid.pandorika.dtos.MusicResponse
import java.util.concurrent.TimeUnit

interface RetrofitService {
  @GET("search")
  fun getResultSearch(@Query("term") term: String): Deferred<MusicResponse>
}

class ApiService {
  private val client = OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .writeTimeout(10, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build()


  val retrofit = Retrofit.Builder()
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create())
      .baseUrl("https://itunes.apple.com/")
      .client(client)
      .build()
      .create(RetrofitService::class.java)

}