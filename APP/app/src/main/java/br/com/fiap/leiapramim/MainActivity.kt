package br.com.fiap.leiapramim

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.fiap.leiapramim.model.Device
import br.com.fiap.leiapramim.route.NavigationGraph
import br.com.fiap.leiapramim.service.DeviceClient
import br.com.fiap.leiapramim.ui.theme.LeiaPraMimTheme
import br.com.fiap.leiapramim.viewmodel.DeviceViewModel
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeiaPraMimTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val deviceViewModel = DeviceViewModel()
                    val deviceState by deviceViewModel.loggedDevice.observeAsState(
                        Device(
                            id = 0,
                            deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID),
                            deviceFactory = Build.MANUFACTURER,
                            deviceModel = Build.MODEL,
                            androidVersion = Build.VERSION.RELEASE.toInt(),
                            sdkVersion = Build.VERSION.SDK_INT.toString(),
                            dateRecord = LocalDateTime.now().toString()
                        )
                    )
                    loginDevice(deviceState, deviceViewModel)

                    val navController = rememberNavController()
                    NavigationGraph(navController, NavigationViewModel(), deviceViewModel)

                }
            }
        }
    }
}

fun loginDevice(localDevice: Device, deviceViewModel: DeviceViewModel) {

    getDeviceBySourceId(localDevice.deviceId) { device ->
        if (device != null) {
            deviceViewModel.changeLoggedDevice(device)
            Log.i("dev_log", "****** LOGIN REALIZADO *********")
            Log.i("dev_log", "${deviceViewModel.loggedDevice.value?.id}")
            Log.i("dev_log", "${deviceViewModel.loggedDevice.value?.deviceId}")
        } else {
            addDevice(localDevice) { dbDevice ->
                deviceViewModel.changeLoggedDevice(dbDevice!!)
                Log.i("dev_log", "****** CRIADO E LOGADO *********")
                Log.i("dev_log", "${deviceViewModel.loggedDevice.value?.id}")
                Log.i("dev_log", "${deviceViewModel.loggedDevice.value?.deviceId}")
            }
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
                Log.e("dev_log", "Response error: ${response.code()}")
                callback(null)
            }
        }
        override fun onFailure(call: Call<Device>, t: Throwable) {
            callback(null)
        }
    })
}

fun addDevice(device: Device, callback: (Device?) -> Unit) {
    val call = DeviceClient().getDeviceService().addDevice(device)

    call.enqueue(object: Callback<Device> {
        override fun onResponse(call: Call<Device>, response: Response<Device>) {
            if (response.isSuccessful) {
                val deviceRes = response.body()
                callback(deviceRes)
            } else {
                Log.e("dev_log", "Response error: ${response.code()}")
                callback(null)
            }
        }
        override fun onFailure(call: Call<Device>, t: Throwable) {
            Log.e("dev_log", "Problem with ${javaClass.enclosingMethod?.name} function")
            Log.e("dev_log", "${t.message}")
            callback(null)
        }
    })
}
