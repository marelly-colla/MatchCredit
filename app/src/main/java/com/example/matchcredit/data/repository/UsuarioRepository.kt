package com.example.matchcredit.data.repository

import com.example.matchcredit.data.local.dao.UsuarioDao
import com.example.matchcredit.data.local.entities.Usuario

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun insertarUsuario(usuario: Usuario): Long {
        return usuarioDao.insertarUsuario(usuario)
    }

    suspend fun actualizarUsuario(usuario: Usuario) {
        usuarioDao.actualizarUsuario(usuario)
    }

    suspend fun obtenerPorCorreo(correo: String): Usuario? {
        return usuarioDao.obtenerPorCorreo(correo)
    }

    suspend fun obtenerPorId(usuarioId: Int): Usuario? {
        return usuarioDao.obtenerPorId(usuarioId)
    }

    suspend fun login(correo: String, contrasena: String): Usuario? {
        return usuarioDao.login(correo, contrasena)
    }
}