package br.com.fiap.leiapramim.view

import android.media.MediaPlayer
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.leiapramim.R
import br.com.fiap.leiapramim.database.repository.ReadImageRepository
import br.com.fiap.leiapramim.model.ReadImage
import br.com.fiap.leiapramim.route.NavigationItem
import br.com.fiap.leiapramim.view.actions.asyncCallApis
import br.com.fiap.leiapramim.view.components.Loading
import br.com.fiap.leiapramim.view.components.PlayButton
import coil.compose.rememberImagePainter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDateTime

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun PreviewScreen(
    navController: NavHostController,
    uriString: String
) {

    val context = LocalContext.current
    val readImageRepository = ReadImageRepository(context)
    val uri = Uri.parse(uriString)
    val mediaPlayer = MediaPlayer()
    var loadingState by remember { mutableStateOf(false) }
    var audioReady by remember { mutableStateOf(false) }
    var readImage by remember { mutableStateOf(ReadImage(0, "", "", LocalDateTime.now())) }

    Box {
        Image(
            painter = rememberImagePainter(data = uri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (!loadingState && !audioReady) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.size(220.dp, 100.dp)
                ) {
                    val columnModifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                    Column(columnModifier) {
                        ButtonPreview(
                            R.drawable.cancel,
                            "Cancel button"
                        ) {
                            navController.popBackStack()
                        }
                    }
                    Column(columnModifier) {
                        ButtonPreview(
                            R.drawable.done,
                            "Done button"
                        ) {

                            loadingState = !loadingState

                            GlobalScope.launch(Dispatchers.IO) {
                                readImage = asyncCallApis(uri)
                                loadingState = !loadingState
                                audioReady = !audioReady
                                readImageRepository.save(readImage)
                            }

                        }
                    }
                }
            }
        }

        if (loadingState && !audioReady) {
            Column {
                Loading()
            }
        }

        if (audioReady) {
            Column {
                val audioFile = readImage.audioPath?.let { File(it) }

                if (audioFile != null) {
                    PlayButton(audioFile, mediaPlayer)
                }

                mediaPlayer.setOnCompletionListener {
                    audioReady = !audioReady
                    navController.navigate(NavigationItem.Camera.route)
                }

            }
        }
    }

    BackHandler(
        enabled = true,
        onBack = {
            mediaPlayer.stop()
            loadingState = false
            audioReady = false
            navController.navigate(NavigationItem.Camera.route)
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}

@Composable
fun ButtonPreview(icon: Int, description: String, action: () -> Unit) {

    IconButton(
        onClick = { action() },
        modifier = Modifier.size(50.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = description,
            tint = Color.Unspecified,
            modifier = Modifier.size(560.dp)
        )
    }
}