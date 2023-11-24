package br.com.fiap.leiapramim.view.actions

import android.net.Uri
import br.com.fiap.leiapramim.model.ReadImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime

suspend fun asyncCallApis(uri: Uri): ReadImage {

    val ocrResponseDeferred = coroutineScope {
        async(Dispatchers.IO) {
            sendOCR(uri)
        }
    }

    var ocrResponse = ""

    val ttsResponseDeferred = coroutineScope {
        async(Dispatchers.IO) {
            ocrResponse = ocrResponseDeferred.await()
            sendTTS(uri, ocrResponse)
        }
    }

    val audioFile = ttsResponseDeferred.await()

    return ReadImage(0, uri.path, ocrResponse, audioFile?.path, LocalDateTime.now())
}