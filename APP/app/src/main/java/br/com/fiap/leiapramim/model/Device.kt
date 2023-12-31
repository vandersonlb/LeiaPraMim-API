package br.com.fiap.leiapramim.model

import androidx.room.TypeConverters
import br.com.fiap.leiapramim.utils.Converters

@TypeConverters(Converters::class)
data class Device(
    val id: Int,
    val deviceId: String,
    val deviceFactory: String,
    val deviceModel: String,
    val androidVersion: Int,
    val sdkVersion: String,
    val dateRecord: String
)