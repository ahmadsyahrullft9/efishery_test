package com.example.testefishery.data.networks

import androidx.viewbinding.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppClient {

    val BASE_URL = "https://stein.efishery.com/v1/storages/5e1edf521073e315924ceab4/"

    private var instance: Retrofit? = null

    fun buildOkHttpClient(): OkHttpClient {
        //logging for development area
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        //config for android lolipop or below
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
            )
            .build()
        val specs = listOf(spec)

        return if (BuildConfig.DEBUG) OkHttpClient.Builder()
            .connectionSpecs(specs)
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        else OkHttpClient.Builder()
            .connectionSpecs(specs)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    }

    fun getInstance(): Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(buildOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return instance!!
    }
}