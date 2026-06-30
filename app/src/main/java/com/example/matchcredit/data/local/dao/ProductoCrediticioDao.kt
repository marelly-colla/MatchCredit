package com.example.matchcredit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matchcredit.data.local.entities.ProductoCrediticio

@Dao
interface ProductoCrediticioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(lista: List<ProductoCrediticio>)

    @Query("SELECT * FROM productos_crediticios")
    suspend fun obtenerTodos(): List<ProductoCrediticio>

    @Query("SELECT * FROM productos_crediticios WHERE productoId = :id")
    suspend fun obtenerPorId(id: Int): ProductoCrediticio?

    @Query("""
        SELECT * FROM productos_crediticios
        WHERE bancoId = :bancoId
    """)
    suspend fun obtenerPorBanco(bancoId: Int): List<ProductoCrediticio>

    @Query("""
        SELECT * FROM productos_crediticios
        WHERE tipoPrestamoId = :tipoPrestamoId
    """)
    suspend fun obtenerPorTipo(tipoPrestamoId: Int): List<ProductoCrediticio>
}