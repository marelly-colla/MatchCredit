package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchcredit.data.dto.ResultadoGuardadoDetalle
import com.example.matchcredit.data.repository.ResultadoGuardadoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DetalleSimulacionGuardadaUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val detalle: ResultadoGuardadoDetalle? = null
)

class DetalleSimulacionGuardadaViewModel(
    private val resultadoId: Int,
    private val resultadoGuardadoRepository: ResultadoGuardadoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleSimulacionGuardadaUiState())
    val uiState: StateFlow<DetalleSimulacionGuardadaUiState> = _uiState

    init {
        cargarDetalle()
    }

    private fun cargarDetalle() {
        viewModelScope.launch {
            try {
                _uiState.value = DetalleSimulacionGuardadaUiState(
                    cargando = true
                )

                val detalle = resultadoGuardadoRepository.obtenerDetalleGuardado(resultadoId)

                if (detalle == null) {
                    _uiState.value = DetalleSimulacionGuardadaUiState(
                        cargando = false,
                        error = "No se encontró la simulación guardada."
                    )
                    return@launch
                }

                _uiState.value = DetalleSimulacionGuardadaUiState(
                    cargando = false,
                    detalle = detalle
                )
            } catch (e: Exception) {
                _uiState.value = DetalleSimulacionGuardadaUiState(
                    cargando = false,
                    error = e.message ?: "No se pudo cargar el detalle guardado."
                )
            }
        }
    }
}