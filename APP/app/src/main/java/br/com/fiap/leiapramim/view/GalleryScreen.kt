package br.com.fiap.leiapramim.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.fiap.leiapramim.database.repository.ReadImageRepository
import br.com.fiap.leiapramim.model.ReadImage
import br.com.fiap.leiapramim.route.NavigationItem
import br.com.fiap.leiapramim.view.components.BottomNavigation
import br.com.fiap.leiapramim.viewmodel.NavigationViewModel
import coil.compose.rememberImagePainter
import java.io.File

@Composable
fun GalleryScreen(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
) {

    val context = LocalContext.current
    val readImageRepository = ReadImageRepository(context)
    var listReadImages by remember { mutableStateOf(listOf<ReadImage>()) }

    Column {

        Column(
            Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(8.dp)
        ) {

            listReadImages = readImageRepository.list()

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp)
            ) {
                items(listReadImages) { readImage ->
                    val imageFile = File("${readImage.imagePath}")
                    Image(
                        painter = rememberImagePainter(data = imageFile),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .height(200.dp)
                            .clickable(
                                true,
                                onClick = {
                                    navController.navigate("${NavigationItem.GalleryPreview.route}/${readImage.id}")
                                }
                            )
                    )

                }
            }

        }

        Column {
            BottomNavigation(navController, navigationViewModel)
        }
    }
}


