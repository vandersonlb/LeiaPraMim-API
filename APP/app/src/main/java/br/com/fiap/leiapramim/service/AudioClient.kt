package br.com.fiap.leiapramim.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AudioClient {

    private val url = "http://192.168.15.28:8080/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAudioService(): AudioService {
        return retrofitFactory.create(AudioService::class.java)
    }
}
