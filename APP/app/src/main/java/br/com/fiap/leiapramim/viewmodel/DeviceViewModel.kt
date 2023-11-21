package br.com.fiap.leiapramim.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.fiap.leiapramim.model.Device

class DeviceViewModel {

    private val _loggedDevice = MutableLiveData<Device>()
    val loggedDevice: LiveData<Device> = _loggedDevice

    fun changeLoggedDevice(screen: Device) {
        _loggedDevice.value = screen
    }
}