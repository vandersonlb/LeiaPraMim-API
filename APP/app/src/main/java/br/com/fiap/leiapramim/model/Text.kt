package br.com.fiap.leiapramim.model

import androidx.room.TypeConverters
import br.com.fiap.leiapramim.utils.Converters

@TypeConverters(Converters::class)
data class Text(
    val id: Int,
    val photoURL: String,
    val text: String,
    val createDate: String,
    val device: Device
)
