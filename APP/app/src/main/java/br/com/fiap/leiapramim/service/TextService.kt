package br.com.fiap.leiapramim.service

import br.com.fiap.leiapramim.model.Device
import br.com.fiap.leiapramim.model.Text
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface TextService {

    @GET("/text")
    fun listAll(): Call<List<Text>>

    @GET("/text/{id}")
    fun getById(@Path("id") id: Int): Call<Text>

    @GET("/text/device/{deviceId}")
    fun listByDeviceId(
        @Path("deviceId") deviceId: Int
    ): Call<List<Text>>

    @POST("/text")
    fun addText(@Body text: Text): Call<Text>
}
