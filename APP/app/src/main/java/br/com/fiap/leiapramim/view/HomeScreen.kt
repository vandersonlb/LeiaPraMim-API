package br.com.fiap.leiapramim.view

import android.media.MediaPlayer
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
import br.com.fiap.leiapramim.view.components.BottomNavigation
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel

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
                            player.start()
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