package com.example.matchcredit.data.repository

import com.example.matchcredit.data.local.dao.TipoPrestamoDao
import com.example.matchcredit.data.local.entities.TipoPrestamo

class TipoPrestamoRepository(
    private val tipoPrestamoDao: TipoPrestamoDao
) {

    suspend fun insertarTodos(lista: List<TipoPrestamo>) {
        tipoPrestamoDao.insertarTodos(lista)
    }

    suspend fun obtenerTodos(): List<TipoPrestamo> {
        return tipoPrestamoDao.obtenerTodos()
    }

    suspend fun obtenerPorId(id: Int): TipoPrestamo? {
        return tipoPrestamoDao.obtenerPorId(id)
    }
}