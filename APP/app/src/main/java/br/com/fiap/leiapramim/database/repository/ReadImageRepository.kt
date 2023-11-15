package br.com.fiap.leiapramim.database.repository

import android.content.Context
import br.com.fiap.leiapramim.database.dao.ReadImageDB
import br.com.fiap.leiapramim.model.ReadImage

class ReadImageRepository(context: Context) {

    private val db = ReadImageDB.getDB(context).readImageDAO()

    fun save(readImage: ReadImage): Long {
        return db.save(readImage)
    }

    fun list(): List<ReadImage> {
        return db.list()
    }

    fun getById(id: Long): ReadImage {
        return db.getById(id)
    }
}
