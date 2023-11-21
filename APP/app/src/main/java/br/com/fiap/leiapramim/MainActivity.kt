package br.com.fiap.leiapramim

import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.fiap.leiapramim.model.Device
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel
import br.com.fiap.leiapramim.route.NavigationGraph
import br.com.fiap.leiapramim.service.DeviceClient
import br.com.fiap.leiapramim.ui.theme.LeiaPraMimTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeiaPraMimTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
                    val deviceFactory = Build.MANUFACTURER
                    val deviceModel = Build.MODEL
                    val androidVersion = Build.VERSION.RELEASE
                    val sdkVersion = Build.VERSION.SDK_INT.toString()
                    val dateRecord = LocalDateTime.now().toString()

//                    val navController = rememberNavController()
//                    NavigationGraph(navController, NavigationViewModel())

                    loginDevice(Device("0", deviceId, deviceFactory, deviceModel, androidVersion, sdkVersion, dateRecord))


                }
            }
        }
    }
}

fun loginDevice(device: Device) {

    getDeviceBySourceId(device.deviceId) { device ->
        if (device != null) {
            Log.i("dev_log", "Esse dispositivo existe no Banco de dados")
            Log.i("dev_log", "${device}")

        } else {
            Log.i("dev_log", "Esse dispositivo NÂO existe no Banco de dados")
            Log.i("dev_log", "${device}")
        }
    }

}

fun getDeviceBySourceId(sourceId: String, callback: (Device?) -> Unit) {
    val call = DeviceClient().getDeviceService().getBySourceDeviceId(sourceId)

    call.enqueue(object : Callback<Device> {
        override fun onResponse(call: Call<Device>, response: Response<Device>) {
            if (response.isSuccessful) {
                val device = response.body()
                callback(device)
            } else {
                Log.e("dev_log", "Erro na resposta: ${response.code()}")
                callback(null)
            }
        }

        override fun onFailure(call: Call<Device>, t: Throwable) {
            callback(null)
        }
    })
}

fun addDevice(device: Device) {

    var call = DeviceClient().getDeviceService().addDevice(device)

    call.enqueue(object: Callback<Device> {
        override fun onResponse(call: Call<Device>, response: Response<Device>) {

            val device = response.body()
            Log.i("dev_log", "Device: $device")

        }

        override fun onFailure(call: Call<Device>, t: Throwable) {
            Log.e("dev_log", "Problem with ${javaClass.enclosingMethod?.name} function")
            Log.e("dev_log", "${t.message}")
        }
    })

}
