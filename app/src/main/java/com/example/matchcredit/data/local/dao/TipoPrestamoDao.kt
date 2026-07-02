package com.example.matchcredit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matchcredit.data.local.entities.TipoPrestamo

@Dao
interface TipoPrestamoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(lista: List<TipoPrestamo>)

    @Query("SELECT * FROM tipos_prestamo ORDER BY nombre")
    suspend fun obtenerTodos(): List<TipoPrestamo>

    @Query("SELECT * FROM tipos_prestamo WHERE tipoPrestamoId = :id")
    suspend fun obtenerPorId(id: Int): TipoPrestamo?
}