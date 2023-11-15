package br.com.fiap.leiapramim.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NavigationViewModel : ViewModel() {

    private val _activeScreen = MutableLiveData<String>()
    val activeScreen: LiveData<String> = _activeScreen

    fun changeActiveScreen(screen: String) {
        _activeScreen.value = screen
    }
}