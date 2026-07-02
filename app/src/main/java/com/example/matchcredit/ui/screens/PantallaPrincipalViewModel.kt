package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchcredit.data.local.entities.PerfilFinanciero
import com.example.matchcredit.data.local.entities.Usuario
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val usuario: Usuario? = null,
    val perfil: PerfilFinanciero? = null,
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val usuarioId: Int,
    private val usuarioRepository: UsuarioRepository,
    private val perfilFinancieroRepository: PerfilFinancieroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val usuario = usuarioRepository.obtenerPorId(usuarioId)
            val perfil = perfilFinancieroRepository.obtenerPorUsuario(usuarioId)
            _uiState.update { it.copy(usuario = usuario, perfil = perfil, isLoading = false) }
        }
    }
}