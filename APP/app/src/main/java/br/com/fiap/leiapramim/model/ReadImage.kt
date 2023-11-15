package br.com.fiap.leiapramim.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.fiap.leiapramim.utils.Converters
import java.time.LocalDateTime

@TypeConverters(Converters::class)
@Entity("tbl_read_image")
data class ReadImage(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cd_read")
    val id: Long = 0,

    @ColumnInfo(name = "image_path")
    val imagePath: String?,

    @ColumnInfo(name = "audio_path")
    val audioPath: String?,

    @ColumnInfo(name = "date")
    val date: LocalDateTime
)