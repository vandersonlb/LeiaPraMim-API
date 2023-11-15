package br.com.fiap.leiapramim.view

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import br.com.fiap.leiapramim.database.repository.ReadImageRepository
import br.com.fiap.leiapramim.view.components.PlayButton
import coil.compose.rememberImagePainter
import java.io.File

@Composable
fun GalleryPreviewScreen(
    readImageId: String
) {

    val readImageRepository = ReadImageRepository(LocalContext.current)
    val readImage = readImageRepository.getById(readImageId.toLong())
    val mediaPlayer = MediaPlayer()

    Box {

        val uriImage = Uri.fromFile(readImage.imagePath?.let { File(it) })
        val audioFile = readImage.audioPath?.let { File(it) }

        Image(
            painter = rememberImagePainter(data = uriImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column {
            if (audioFile != null) PlayButton(audioFile, mediaPlayer)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
