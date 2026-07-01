package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import com.example.matchcredit.data.local.entities.Usuario
import com.example.matchcredit.data.repository.UsuarioRepository

class LoginViewModel(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    suspend fun login(
        correo: String,
        contrasena: String
    ): Usuario? {

        return usuarioRepository.login(
            correo,
            contrasena
        )
    }
}