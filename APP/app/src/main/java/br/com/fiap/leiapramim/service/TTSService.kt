package br.com.fiap.leiapramim.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TTSService {

    @GET("?key=dbb3f4b4345f46fdbc381da41e02628a&hl=pt-br")
    fun getSpeech(@Query("src") ocrText: String): Call<ResponseBody>

}
