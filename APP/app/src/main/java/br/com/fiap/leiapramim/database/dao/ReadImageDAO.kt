package br.com.fiap.leiapramim.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.fiap.leiapramim.model.ReadImage

@Dao
interface ReadImageDAO {
    @Insert
    fun save(readImage: ReadImage): Long

    @Query("SELECT * FROM tbl_read_image WHERE cd_read = :id")
    fun getById(id: Long): ReadImage

    @Query("SELECT * FROM tbl_read_image")
    fun list(): List<ReadImage>
}