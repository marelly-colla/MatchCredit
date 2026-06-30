package com.example.matchcredit.data.repository

import com.example.matchcredit.data.local.dao.BancoDao
import com.example.matchcredit.data.local.entities.Banco

class BancoRepository(
    private val bancoDao: BancoDao
) {
    suspend fun obtenerTodos(): List<Banco> {
        return bancoDao.obtenerTodos()
    }

    suspend fun obtenerPorId(id: Int): Banco? {
        return bancoDao.obtenerPorId(id)
    }

    suspend fun insertarTodos(bancos: List<Banco>) {
        bancoDao.insertarTodos(bancos)
    }
}