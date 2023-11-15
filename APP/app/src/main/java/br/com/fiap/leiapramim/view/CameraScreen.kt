package br.com.fiap.leiapramim.view

import android.content.res.Resources
import android.util.Size
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import br.com.fiap.leiapramim.R
import br.com.fiap.leiapramim.view.actions.takePicture
import br.com.fiap.leiapramim.view.components.BottomNavigation
import br.com.fiap.leiapramim.view.components.CameraView
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel

@Composable
fun CameraScreen(navController: NavHostController, navigationViewModel: NavigationViewModel) {

    val context = LocalContext.current
    val cameraExecutor = ContextCompat.getMainExecutor(context)
    val screenWith = Resources.getSystem().displayMetrics.widthPixels / 4
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels / 4
    val imageCapture = ImageCapture.Builder()
        .setTargetResolution(Size(screenWith, screenHeight))
        .build()

    Box {

        CameraView(context, imageCapture, cameraExecutor)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row {

                IconButton(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    onClick = {
                        takePicture(context, imageCapture, cameraExecutor, navController)
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.click),
                        contentDescription = "Click Button",
                        tint = Color.Unspecified,
                    )
                }
            }

            Row {
                BottomNavigation(navController, navigationViewModel)
            }
        }
    }
}