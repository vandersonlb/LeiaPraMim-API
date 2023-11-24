package br.com.fiap.leiapramim.view

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import br.com.fiap.leiapramim.model.Audio
import br.com.fiap.leiapramim.model.Device
import br.com.fiap.leiapramim.model.ReadImage
import br.com.fiap.leiapramim.model.Text
import br.com.fiap.leiapramim.route.NavigationItem
import br.com.fiap.leiapramim.service.AudioClient
import br.com.fiap.leiapramim.service.TextClient
import br.com.fiap.leiapramim.view.actions.asyncCallApis
import br.com.fiap.leiapramim.view.components.Loading
import br.com.fiap.leiapramim.view.components.PlayButton
import br.com.fiap.leiapramim.viewmodel.DeviceViewModel
import coil.compose.rememberImagePainter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.time.LocalDateTime

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun PreviewScreen(
    navController: NavHostController,
    uriString: String,
    deviceViewModel: DeviceViewModel
) {

    val context = LocalContext.current
    val readImageRepository = ReadImageRepository(context)
    val uri = Uri.parse(uriString)
    val mediaPlayer = MediaPlayer()
    var loadingState by remember { mutableStateOf(false) }
    var audioReady by remember { mutableStateOf(false) }
    var readImage by remember { mutableStateOf(ReadImage(0, "", "", "", LocalDateTime.now())) }

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

                                val device = deviceViewModel.loggedDevice.value
                                addText(device!!, readImage) {textDB ->
                                    addAudio(textDB!!, readImage)
                                }
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

fun addText(device: Device, readImage: ReadImage, callback: (Text?) -> Unit) {
    val text = Text(0, readImage.imagePath!!, readImage.textOcr!!, readImage.date.toString(), device)
    val call = TextClient().getTextService().addText(text)

    call.enqueue(object : Callback<Text> {
        override fun onResponse(call: Call<Text>, response: Response<Text>) {
            if (response.isSuccessful) {
                val textRes = response.body()
                callback(textRes)
            } else {
                Log.e("dev_log", "Response error: ${response.code()}")
                callback(null)
            }
        }
        override fun onFailure(call: Call<Text>, t: Throwable) {
            Log.e("dev_log", "Problem with ${javaClass.enclosingMethod?.name} function")
            Log.e("dev_log", "${t.message}")
            callback(null)
        }
    })
}

fun addAudio(text: Text, readImage: ReadImage) {
    val audio = Audio(0, readImage.audioPath!!, readImage.date.toString(), text)
    val call = AudioClient().getAudioService().addAudio(audio)

    call.enqueue(object : Callback<Audio>{
        override fun onResponse(call: Call<Audio>, response: Response<Audio>) {
            val audioRes = response.body()
            Log.i("dev_log", "Text: $text added")
            Log.i("dev_log", "Audio: $audioRes added")
        }
        override fun onFailure(call: Call<Audio>, t: Throwable) {
            Log.e("dev_log", "Problem with ${javaClass.enclosingMethod?.name} function")
            Log.e("dev_log", "${t.message}")
        }
    })
}
