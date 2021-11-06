package tech.volkov.nextdoorfarm.backend.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitBuilder {
    companion object {
        private const val BASE_URL = "http://yandex.ru/"
        private val builder = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit: Retrofit = builder.build()
    }
}
