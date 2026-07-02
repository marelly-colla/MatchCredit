package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchcredit.data.local.entities.PerfilFinanciero
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ConsultaPrestamoUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val perfil: PerfilFinanciero? = null
)

class ConsultaPrestamoViewModel(
    private val usuarioId: Int,
    private val perfilFinancieroRepository: PerfilFinancieroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConsultaPrestamoUiState())
    val uiState: StateFlow<ConsultaPrestamoUiState> = _uiState

    init {
        cargarPerfil()
    }

    private fun cargarPerfil() {
        viewModelScope.launch {
            try {
                _uiState.value = ConsultaPrestamoUiState(
                    cargando = true
                )

                val perfil = perfilFinancieroRepository.obtenerPorUsuario(usuarioId)

                _uiState.value = ConsultaPrestamoUiState(
                    cargando = false,
                    perfil = perfil
                )
            } catch (e: Exception) {
                _uiState.value = ConsultaPrestamoUiState(
                    cargando = false,
                    error = e.message ?: "No se pudo cargar el perfil financiero."
                )
            }
        }
    }
}