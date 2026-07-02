package com.example.matchcredit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matchcredit.data.dto.ProductoCrediticioDetalle
import com.example.matchcredit.data.local.entities.ProductoCrediticio

@Dao
interface ProductoCrediticioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(lista: List<ProductoCrediticio>)

    @Query("""
SELECT
    pc.productoId,
    pc.nombreProducto,
    b.nombre AS bancoNombre,
    tp.nombre AS tipoPrestamoNombre,
    pc.minTrabajoMeses,
    pc.montoMin,
    pc.montoMax,
    pc.plazoMinMeses,
    pc.plazoMaxMeses,
    pc.ingresoMin,
    pc.seguroDesgravamenMensualPct,
    pc.comisionMensual,
    pc.gastoAdministrativo
FROM productos_crediticios pc
INNER JOIN bancos b
    ON pc.bancoId = b.bancoId
INNER JOIN tipos_prestamo tp
    ON pc.tipoPrestamoId = tp.tipoPrestamoId
""")
    suspend fun obtenerTodos(): List<ProductoCrediticioDetalle>

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