package br.com.fiap.leiapramim.service

import br.com.fiap.leiapramim.model.OCRModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface OCRService {

    @Headers("apikey: K84662716688957")
    @Multipart
    @POST("parse/image")
    fun postImage(
        @Part image: MultipartBody.Part,
        @Part("language") language: RequestBody = RequestBody.create(MediaType.parse("text/plain"),"por")
    ): Call<OCRModel>

}
