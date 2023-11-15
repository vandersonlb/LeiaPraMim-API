package br.com.fiap.leiapramim.view.actions

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.navigation.NavHostController
import br.com.fiap.leiapramim.route.NavigationItem
import java.io.File
import java.util.concurrent.Executor

fun takePicture(
    context: Context,
    imageCapture: ImageCapture,
    cameraExecutor: Executor,
    navController: NavHostController
) {

//    val picturesDirectory = context.filesDir
    val picturesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val outputFile = File(picturesDirectory, "${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()

    imageCapture.takePicture(
        outputOptions,
        cameraExecutor,
        object : ImageCapture.OnImageSavedCallback {

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(outputFile)
                val escapedUri = Uri.encode(savedUri.toString())
                navController.navigate("${NavigationItem.Preview.route}/$escapedUri")
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("test", "$exception")
            }
        }
    )
}