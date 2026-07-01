package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchcredit.data.dto.ResultadoGuardadoResumen
import com.example.matchcredit.data.repository.ResultadoGuardadoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HistorialSimulacionesUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val simulaciones: List<ResultadoGuardadoResumen> = emptyList()
)

class HistorialSimulacionesViewModel(
    private val usuarioId: Int,
    private val resultadoGuardadoRepository: ResultadoGuardadoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistorialSimulacionesUiState())
    val uiState: StateFlow<HistorialSimulacionesUiState> = _uiState

    init {
        cargarSimulaciones()
    }

    fun cargarSimulaciones() {
        viewModelScope.launch {
            try {
                _uiState.value = HistorialSimulacionesUiState(
                    cargando = true
                )

                val simulaciones = resultadoGuardadoRepository.obtenerResumen(usuarioId)

                _uiState.value = HistorialSimulacionesUiState(
                    cargando = false,
                    simulaciones = simulaciones
                )
            } catch (e: Exception) {
                _uiState.value = HistorialSimulacionesUiState(
                    cargando = false,
                    error = e.message ?: "No se pudieron cargar las simulaciones guardadas."
                )
            }
        }
    }
}