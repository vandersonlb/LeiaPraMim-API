package br.com.fiap.leiapramim.view

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.leiapramim.R
import br.com.fiap.leiapramim.model.Device
import br.com.fiap.leiapramim.service.DeviceClient
import br.com.fiap.leiapramim.view.components.BottomNavigation
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

@Composable
fun HomeScreen(navController: NavHostController, navigationViewModel: NavigationViewModel) {

    val context = LocalContext.current
    var player by remember { mutableStateOf(MediaPlayer.create(context, R.raw.intro)) }
    var playState by remember { mutableStateOf(true) }

    Column {
        Row(
            Modifier
                .fillMaxSize()
                .weight(1f),
        ) {

            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (playState) {
                    IconButton(
                        onClick = {
                            playState = false
                            if (player == null) player = MediaPlayer.create(context, R.raw.intro)
//                            player.start()
//                            getInfo(context)
//                            listAllDevice()
//                            getDeviceById("1")
//                            getDeviceBySourceId("rm94177")
                            addDevice()
                        },
                        Modifier.size(256.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.play),
                            contentDescription = "",
                            tint = Color.Unspecified,
                        )
                    }

                } else {
                    IconButton(
                        onClick = {
                            playState = true
                            player.pause()
                        },
                        Modifier.size(256.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.audio),
                            contentDescription = "",
                            tint = Color.Unspecified,
                        )
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        player.release()
                    }
                }

            }

        }
        Row {
            BottomNavigation(navController, navigationViewModel)
        }
    }
}

fun getInfo(context: Context) {

    val sourceDeviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    val deviceFactory = Build.MANUFACTURER
    val deviceModel = Build.MODEL
    val androidVersion = Build.VERSION.RELEASE
    val sdkVersion = Build.VERSION.SDK_INT
    val dateRecord = LocalDateTime.now()

    Log.i("dev_log", sourceDeviceId)         // 6152fa3f995ddbc5
    Log.i("dev_log", deviceModel)            // moto g(20)
    Log.i("dev_log", deviceFactory)          // motorola
    Log.i("dev_log", androidVersion)         // 11
    Log.i("dev_log", "${sdkVersion}")   // 30
    Log.i("dev_log", "${dateRecord}")   // 2023-11-17T18:04:31.149
}


fun listAllDevice() {

    var call = DeviceClient().getDeviceService().listAll()

    call.enqueue(object: Callback<List<Device>>{
        override fun onResponse(call: Call<List<Device>>, response: Response<List<Device>>) {

            val devices = response.body()
            devices?.forEach { device ->
                Log.i("dev_log", "Device: $device")
            }

        }

        override fun onFailure(call: Call<List<Device>>, t: Throwable) {
            Log.e("dev_log", "Deu ruim")
            Log.e("dev_log", "${t.stackTrace}")
            Log.e("dev_log", "${t.message}")
            Log.e("dev_log", "${t.cause}")
            Log.e("dev_log", "${t.localizedMessage}")
        }
    })

}


fun getDeviceById(id: String) {

    var call = DeviceClient().getDeviceService().getById(id)

    call.enqueue(object: Callback<Device>{
        override fun onResponse(call: Call<Device>, response: Response<Device>) {

            val device = response.body()
            Log.i("dev_log", "Device: $device")

        }

        override fun onFailure(call: Call<Device>, t: Throwable) {
            Log.e("dev_log", "Deu ruim")
            Log.e("dev_log", "${t.stackTrace}")
            Log.e("dev_log", "${t.message}")
            Log.e("dev_log", "${t.cause}")
            Log.e("dev_log", "${t.localizedMessage}")
        }
    })

}

fun getDeviceBySourceId(sourceId: String) {

    var call = DeviceClient().getDeviceService().getBySourceDeviceId(sourceId)

    call.enqueue(object: Callback<Device>{
        override fun onResponse(call: Call<Device>, response: Response<Device>) {

            val device = response.body()
            Log.i("dev_log", "Device: $device")

        }

        override fun onFailure(call: Call<Device>, t: Throwable) {
            Log.e("dev_log", "Deu ruim")
            Log.e("dev_log", "${t.stackTrace}")
            Log.e("dev_log", "${t.message}")
            Log.e("dev_log", "${t.cause}")
            Log.e("dev_log", "${t.localizedMessage}")
        }
    })

}

fun addDevice() {

    val device = Device("0", "xxxxxx", "Motorola", "Moto G20", "Choco 30", "11", "2021-12-01")

    var call = DeviceClient().getDeviceService().addDevice(device)

    call.enqueue(object: Callback<Device>{
        override fun onResponse(call: Call<Device>, response: Response<Device>) {

            val device = response.body()
            Log.i("dev_log", "Device: $device")

        }

        override fun onFailure(call: Call<Device>, t: Throwable) {
            Log.e("dev_log", "Deu ruim")
            Log.e("dev_log", "${t.stackTrace}")
            Log.e("dev_log", "${t.message}")
            Log.e("dev_log", "${t.cause}")
            Log.e("dev_log", "${t.localizedMessage}")
        }
    })

}