package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import com.example.matchcredit.data.local.entities.Usuario
import com.example.matchcredit.data.repository.UsuarioRepository

class RegistroViewModel(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    suspend fun registrarUsuario(
        nombres: String,
        correo: String,
        contrasena: String,
        edad: Int
    ): Long {

        val usuario = Usuario(
            nombres = nombres,
            correo = correo,
            contrasena = contrasena,
            edad = edad
        )

        return usuarioRepository.insertarUsuario(usuario)
    }
}