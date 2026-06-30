package com.example.matchcredit.data.repository

import com.example.matchcredit.data.local.dao.ProductoCrediticioDao
import com.example.matchcredit.data.local.entities.ProductoCrediticio

class ProductoCrediticioRepository(
    private val productoCrediticioDao: ProductoCrediticioDao
) {

    suspend fun insertarTodos(lista: List<ProductoCrediticio>) {
        productoCrediticioDao.insertarTodos(lista)
    }

    suspend fun obtenerTodos(): List<ProductoCrediticio> {
        return productoCrediticioDao.obtenerTodos()
    }

    suspend fun obtenerPorId(id: Int): ProductoCrediticio? {
        return productoCrediticioDao.obtenerPorId(id)
    }

    suspend fun obtenerPorBanco(bancoId: Int): List<ProductoCrediticio> {
        return productoCrediticioDao.obtenerPorBanco(bancoId)
    }

    suspend fun obtenerPorTipo(tipoPrestamoId: Int): List<ProductoCrediticio> {
        return productoCrediticioDao.obtenerPorTipo(tipoPrestamoId)
    }
}