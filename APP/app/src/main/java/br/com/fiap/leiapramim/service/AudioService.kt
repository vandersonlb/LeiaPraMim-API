package br.com.fiap.leiapramim.service

import br.com.fiap.leiapramim.model.Audio
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface AudioService {

    @GET("/audio")
    fun listAll(): Call<List<Audio>>

    @GET("/audio/{id}")
    fun getById(@Path("id") id: Int): Call<Audio>

    @GET("/audio/text/{textId}")
    fun getByTextId(
        @Path("textId") textId: Int
    ): Call<Audio>

    @POST("/audio")
    fun addAudio(@Body audio: Audio): Call<Audio>
}
