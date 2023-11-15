package br.com.fiap.leiapramim.route

import br.com.fiap.leiapramim.R

sealed class NavigationItem(var title: String, var icon: Int, var route: String) {
    object Home : NavigationItem("Home", R.drawable.home, "home")
    object Camera : NavigationItem("Camera", R.drawable.cam, "camera")
    object Gallery : NavigationItem("Gallery", R.drawable.gallery, "gallery")
    object Preview : NavigationItem("Preview Image", R.drawable.gallery, "preview")
    object GalleryPreview : NavigationItem("Gallery Preview Image", R.drawable.gallery, "galleryPreview")

}