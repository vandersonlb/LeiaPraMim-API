package br.com.fiap.leiapramim.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.leiapramim.route.NavigationItem
import br.com.fiap.leiapramim.ui.theme.Black
import br.com.fiap.leiapramim.ui.theme.Orange
import br.com.fiap.leiapramim.ui.theme.White
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel

@Composable
fun BottomNavigation(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
) {

    val activeScreen by navigationViewModel.activeScreen.observeAsState(NavigationItem.Home.title)

    val navItems = listOf(
        NavigationItem.Home,
        NavigationItem.Camera,
        NavigationItem.Gallery
    )

    BottomAppBar(
        actions = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    navItems.forEach {
                        IconButton(
                            onClick = {
                                navController.navigate(it.route)
                                navigationViewModel.changeActiveScreen(it.title)
                            },
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .size(54.dp)
                                .clip(CircleShape),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = if (activeScreen == it.title) Orange else Black,
                                contentColor = White,
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = it.title,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
        },
        containerColor = Black,
        contentColor = White,
        contentPadding = PaddingValues(8.dp),
    )
}