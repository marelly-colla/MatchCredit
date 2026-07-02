package com.example.matchcredit.data.repository

import com.example.matchcredit.data.local.dao.PerfilFinancieroDao
import com.example.matchcredit.data.local.entities.PerfilFinanciero

class PerfilFinancieroRepository(
    private val perfilFinancieroDao: PerfilFinancieroDao
) {

    suspend fun insertar(perfil: PerfilFinanciero) {
        perfilFinancieroDao.insertar(perfil)
    }

    suspend fun actualizar(perfil: PerfilFinanciero) {
        perfilFinancieroDao.actualizar(perfil)
    }

    suspend fun obtenerPorUsuario(usuarioId: Int): PerfilFinanciero? {
        return perfilFinancieroDao.obtenerPorUsuario(usuarioId)
    }
}