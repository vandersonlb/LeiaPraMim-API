package br.com.fiap.leiapramim.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OCRClient {

    private val url = "https://api.ocr.space/"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getOCRService(): OCRService {
        return retrofit.create(OCRService::class.java)
    }

}