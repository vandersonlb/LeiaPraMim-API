package br.com.fiap.leiapramim.view.actions

import android.net.Uri
import br.com.fiap.leiapramim.model.OCRModel
import br.com.fiap.leiapramim.service.OCRClient
import kotlinx.coroutines.CompletableDeferred
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

suspend fun sendOCR(uri: Uri): String {
    val deferred = CompletableDeferred<String>()

    val file = File(uri.path ?: "")
    val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
    val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

    val call = OCRClient().getOCRService().postImage(body)

    call.enqueue(object : Callback<OCRModel> {
        override fun onResponse(
            call: Call<OCRModel>,
            response: Response<OCRModel>
        ) {
            val ocrResponse = response.body()

            if (ocrResponse != null && ocrResponse.ocrExitCode != 1)
                deferred.completeExceptionally(Exception("Failed to read the image"))

            val parsedText = ocrResponse?.parsedResults?.getOrNull(0)?.parsedText ?: ""
            deferred.complete(parsedText)
        }

        override fun onFailure(call: Call<OCRModel>, t: Throwable) {
            deferred.completeExceptionally(t)
        }
    })

    return deferred.await()
}