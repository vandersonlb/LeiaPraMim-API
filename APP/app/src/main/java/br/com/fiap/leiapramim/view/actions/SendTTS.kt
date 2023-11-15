package br.com.fiap.leiapramim.view.actions

import android.net.Uri
import android.util.Log
import br.com.fiap.leiapramim.service.TTSClient
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

suspend fun sendTTS(uri: Uri, ocrText: String): File? {

    val path = uri.path?.substringBeforeLast("/")
    val filename = uri.lastPathSegment?.substringBeforeLast(".")
    val deferred = CompletableDeferred<File?>()

    val call = TTSClient().getTTSService().getSpeech(ocrText)

    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
        ) {

            if (response.isSuccessful) {
                val audioBytes = response.body()?.bytes()

                if (audioBytes != null) {
                    val audioFile = File(path, "${filename}.mp3")
                    FileOutputStream(audioFile).use { outputStream ->
                        outputStream.write(audioBytes)
                    }
                    deferred.complete(audioFile)
                }
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.e("test", "$t")
            deferred.complete(null)
        }

    })
    return withContext(Dispatchers.IO) { deferred.await() }
}