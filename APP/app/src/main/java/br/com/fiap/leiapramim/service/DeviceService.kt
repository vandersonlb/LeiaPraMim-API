package br.com.fiap.leiapramim.service

import br.com.fiap.leiapramim.model.Device
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface DeviceService {

//    @GET("/device")
//    fun listAll(): Call<List<Device>>

//    @GET("/device/{id}")
//    fun getById(@Path("id") id: Int): Call<Device>

    @GET("/device/source_device/{deviceId}")
    fun getBySourceDeviceId(
        @Path("deviceId") deviceId: String
    ): Call<Device>

    @POST("/device")
    fun addDevice(@Body device: Device): Call<Device>
}
