package com.example.matchcredit.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.matchcredit.data.dto.ResultadoGuardadoResumen
import com.example.matchcredit.data.local.entities.ResultadoGuardado

@Dao
interface ResultadoGuardadoDao {

    @Insert
    suspend fun guardar(resultado: ResultadoGuardado): Long

    @Delete
    suspend fun eliminar(resultado: ResultadoGuardado)

    @Query("DELETE FROM resultados_guardados WHERE resultadoId = :id")
    suspend fun eliminarPorId(id: Int)

    @Query("""
        SELECT * FROM resultados_guardados
        WHERE usuarioId = :usuarioId
        ORDER BY fechaSimulacion DESC
    """)
    suspend fun obtenerPorUsuario(usuarioId: Int): List<ResultadoGuardado>

    @Query("""
        SELECT * FROM resultados_guardados
        WHERE resultadoId = :id
    """)
    suspend fun obtenerPorId(id: Int): ResultadoGuardado?

    @Query("""
        SELECT 
            r.montoSolicitado,
            r.plazoMeses,
            r.fechaSimulacion,
            r.scoreUsado,
            r.nivelRiesgoUsado,
            r.teaUsadaPct,
            r.cuotaEstimada,
            r.costoTotalEstimado,

            p.nombreProducto AS nombreProducto,
            b.nombre AS nombreBanco

        FROM resultados_guardados r
        INNER JOIN productos_crediticios p 
            ON r.productoId = p.productoId
        INNER JOIN bancos b 
            ON p.bancoId = b.bancoId
        WHERE r.usuarioId = :usuarioId
        ORDER BY r.fechaSimulacion DESC
    """)
    suspend fun obtenerResumen(usuarioId: Int): List<ResultadoGuardadoResumen>
}