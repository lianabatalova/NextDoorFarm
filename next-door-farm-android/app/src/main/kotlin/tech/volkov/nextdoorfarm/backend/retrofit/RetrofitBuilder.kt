package tech.volkov.nextdoorfarm.backend.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.time.temporal.ChronoField
import java.util.concurrent.TimeUnit

class RetrofitBuilder {
    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"

        private const val BASE_URL = "https://next-door-farm-imtu2o6vga-uc.a.run.app"
        private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        private val builder = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit = builder.build()
    }
}
