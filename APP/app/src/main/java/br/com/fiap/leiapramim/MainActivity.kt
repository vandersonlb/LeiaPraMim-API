package br.com.fiap.leiapramim

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel
import br.com.fiap.leiapramim.route.NavigationGraph
import br.com.fiap.leiapramim.ui.theme.LeiaPraMimTheme

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

                    val deviceManufacturer = Build.MANUFACTURER   // Fabricante do cel.
                    val deviceModel = Build.MODEL                 // Modelo do cel.
                    val androidVersion = Build.VERSION.RELEASE    // Versão do Android.
                    val sdkVersion = Build.VERSION.SDK_INT        // Versão do SDK/API.
ds
                    val navController = rememberNavController()
                    NavigationGraph(navController, NavigationViewModel())
                }
            }
        }
    }
}
