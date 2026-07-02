package com.example.matchcredit.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.matchcredit.data.dto.ResultadoGuardadoResumen
import com.example.matchcredit.data.local.entities.ResultadoGuardado
import com.example.matchcredit.data.dto.ResultadoGuardadoDetalle

@Dao
interface ResultadoGuardadoDao {

    @Insert
    suspend fun guardar(resultado: ResultadoGuardado): Long

    @Delete
    suspend fun eliminar(resultado: ResultadoGuardado)

    @Query("DELETE FROM resultados_guardados WHERE resultadoId = :id")
    suspend fun eliminarPorId(id: Int)

    @Query("""
        SELECT COUNT(*) FROM resultados_guardados
        WHERE usuarioId = :usuarioId
        AND productoId = :productoId
        AND montoSolicitado = :montoSolicitado
        AND plazoMeses = :plazoMeses
    """)
    suspend fun contarSimulacionExistente(
        usuarioId: Int,
        productoId: Int,
        montoSolicitado: Double,
        plazoMeses: Int
    ): Int

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
            r.resultadoId,
            r.productoId,
            r.ranking,
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

    @Query("""
    SELECT 
        r.resultadoId,
        r.usuarioId,
        r.tipoPrestamoId,
        r.productoId,

        p.nombreProducto AS nombreProducto,
        b.nombre AS nombreBanco,

        r.montoSolicitado,
        r.plazoMeses,
        r.fechaSimulacion,

        r.scoreUsado,
        r.nivelRiesgoUsado,

        r.cumpleFiltros,
        r.cumpleCapacidadPago,
        r.motivosExclusion,

        r.teaUsadaPct,
        r.temCalculada,
        r.cuotaBase,
        r.seguroMensual,
        r.cuotaEstimada,
        r.costoTotalEstimado,
        r.interesYSeguroTotal,
        r.ratioPostCredito,
        r.ranking

    FROM resultados_guardados r
    INNER JOIN productos_crediticios p 
        ON r.productoId = p.productoId
    INNER JOIN bancos b 
        ON p.bancoId = b.bancoId
    WHERE r.resultadoId = :resultadoId
    """)
    suspend fun obtenerDetalleGuardado(resultadoId: Int): ResultadoGuardadoDetalle?
}