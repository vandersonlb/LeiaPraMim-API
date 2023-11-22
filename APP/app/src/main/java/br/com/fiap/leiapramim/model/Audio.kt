package br.com.fiap.leiapramim.model

import androidx.room.TypeConverters
import br.com.fiap.leiapramim.utils.Converters

@TypeConverters(Converters::class)
data class Audio(
    val id: Int,
    val audioURL: String,
    val createDate: String,
    val text: Text
)
