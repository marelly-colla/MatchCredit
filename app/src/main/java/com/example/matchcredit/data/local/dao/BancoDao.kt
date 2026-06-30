package com.example.matchcredit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matchcredit.data.local.entities.Banco

@Dao
interface BancoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(bancos: List<Banco>)

    @Query("SELECT * FROM bancos ORDER BY nombre")
    suspend fun obtenerTodos(): List<Banco>

    @Query("SELECT * FROM bancos WHERE bancoId = :id")
    suspend fun obtenerPorId(id: Int): Banco?
}