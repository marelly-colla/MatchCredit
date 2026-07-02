package com.example.matchcredit.data.repository

import com.example.matchcredit.data.dto.ResultadoGuardadoResumen
import com.example.matchcredit.data.local.dao.ResultadoGuardadoDao
import com.example.matchcredit.data.local.entities.ResultadoGuardado
import com.example.matchcredit.data.dto.ResultadoGuardadoDetalle

class ResultadoGuardadoRepository(
    private val resultadoGuardadoDao: ResultadoGuardadoDao
) {

    suspend fun guardar(resultado: ResultadoGuardado): Long {
        return resultadoGuardadoDao.guardar(resultado)
    }

    suspend fun eliminar(resultado: ResultadoGuardado) {
        resultadoGuardadoDao.eliminar(resultado)
    }

    suspend fun eliminarPorId(id: Int) {
        resultadoGuardadoDao.eliminarPorId(id)
    }

    suspend fun existeSimulacion(
        usuarioId: Int,
        productoId: Int,
        montoSolicitado: Double,
        plazoMeses: Int
    ): Boolean {
        return resultadoGuardadoDao.contarSimulacionExistente(
            usuarioId = usuarioId,
            productoId = productoId,
            montoSolicitado = montoSolicitado,
            plazoMeses = plazoMeses
        ) > 0
    }

    suspend fun obtenerPorUsuario(usuarioId: Int): List<ResultadoGuardado> {
        return resultadoGuardadoDao.obtenerPorUsuario(usuarioId)
    }

    suspend fun obtenerPorId(id: Int): ResultadoGuardado? {
        return resultadoGuardadoDao.obtenerPorId(id)
    }

    suspend fun obtenerResumen(usuarioId: Int): List<ResultadoGuardadoResumen> {
        return resultadoGuardadoDao.obtenerResumen(usuarioId)
    }

    suspend fun obtenerDetalleGuardado(resultadoId: Int): ResultadoGuardadoDetalle? {
        return resultadoGuardadoDao.obtenerDetalleGuardado(resultadoId)
    }
}