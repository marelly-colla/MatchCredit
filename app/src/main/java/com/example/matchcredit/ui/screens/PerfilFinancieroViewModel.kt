package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.UsuarioRepository
import com.example.matchcredit.data.local.entities.PerfilFinanciero
import com.example.matchcredit.domain.calculator.FinancialCalculator
import com.example.matchcredit.domain.calculator.ScoreCalculator
import com.example.matchcredit.domain.enums.ClasificacionDeclarada
import com.example.matchcredit.domain.enums.TipoTrabajo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PerfilFinancieroUiState(
    val tipoTrabajo: TipoTrabajo = TipoTrabajo.PLANILLA,
    val ingresoMensual: String = "",
    val gastosMensuales: String = "",
    val deudaTotalActual: String = "",
    val cuotaMensualDeudas: String = "",
    val antiguedadTrabajandoMeses: String = "",
    val clasificacionSbs: ClasificacionDeclarada = ClasificacionDeclarada.DESCONOCIDO,
    val tieneAhorros: Boolean = false,
    val montoAhorros: String = "",
    val isLoading: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val error: String? = null
)

class PerfilFinancieroViewModel(
    val usuarioId: Int,
    private val perfilFinancieroRepository: PerfilFinancieroRepository,
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilFinancieroUiState())
    val uiState: StateFlow<PerfilFinancieroUiState> = _uiState.asStateFlow()

    private var edadUsuario: Int = 0
    private val scoreCalculator = ScoreCalculator()

    init {
        viewModelScope.launch {
            edadUsuario = usuarioRepository.obtenerPorId(usuarioId)?.edad ?: 0
        }
    }

    fun onTipoTrabajoChange(value: TipoTrabajo) = _uiState.update { it.copy(tipoTrabajo = value) }
    fun onIngresoChange(value: String) = _uiState.update { it.copy(ingresoMensual = value) }
    fun onGastosChange(value: String) = _uiState.update { it.copy(gastosMensuales = value) }
    fun onDeudaChange(value: String) = _uiState.update { it.copy(deudaTotalActual = value) }
    fun onCuotaChange(value: String) = _uiState.update { it.copy(cuotaMensualDeudas = value) }
    fun onAntiguedadChange(value: String) = _uiState.update { it.copy(antiguedadTrabajandoMeses = value) }
    fun onClasificacionChange(value: ClasificacionDeclarada) = _uiState.update { it.copy(clasificacionSbs = value) }
    fun onTieneAhorrosChange(value: Boolean) = _uiState.update { it.copy(tieneAhorros = value, montoAhorros = if (!value) "" else it.montoAhorros) }
    fun onMontoAhorrosChange(value: String) = _uiState.update { it.copy(montoAhorros = value) }
    fun clearError() = _uiState.update { it.copy(error = null) }

    fun calcularYGuardar() {
        val state = _uiState.value

        val ingreso = state.ingresoMensual.toDoubleOrNull() ?: run {
            _uiState.update { it.copy(error = "Ingreso mensual inválido") }
            return
        }
        val gastos = state.gastosMensuales.toDoubleOrNull() ?: run {
            _uiState.update { it.copy(error = "Gastos mensuales inválidos") }
            return
        }
        val deuda = state.deudaTotalActual.toDoubleOrNull() ?: run {
            _uiState.update { it.copy(error = "Deuda total inválida") }
            return
        }
        val cuota = state.cuotaMensualDeudas.toDoubleOrNull() ?: run {
            _uiState.update { it.copy(error = "Cuota mensual inválida") }
            return
        }
        val antiguedad = state.antiguedadTrabajandoMeses.toIntOrNull() ?: run {
            _uiState.update { it.copy(error = "Antigüedad inválida") }
            return
        }
        val ahorros = if (state.tieneAhorros) state.montoAhorros.toDoubleOrNull() ?: 0.0 else 0.0

        val ratio = FinancialCalculator.calcularRatioEndeudamiento(ingreso, gastos, cuota)
        val capacidadPago = FinancialCalculator.calcularCapacidadPagoDisponible(ingreso, gastos, cuota)

        val scoreResult = scoreCalculator.calcularScore(
            ingresoMensual = ingreso,
            gastosMensuales = gastos,
            cuotaMensualDeudas = cuota,
            tipoTrabajo = state.tipoTrabajo,
            antiguedadMeses = antiguedad,
            clasificacionCrediticia = state.clasificacionSbs,
            montoAhorros = ahorros,
            edad = edadUsuario
        )

        val perfil = PerfilFinanciero(
            usuarioId = usuarioId,
            tipoTrabajo = state.tipoTrabajo,
            ingresoMensual = ingreso,
            gastosMensuales = gastos,
            deudaTotalActual = deuda,
            cuotaMensualDeudas = cuota,
            antiguedadTrabajandoMeses = antiguedad,
            clasificacionSbsDeclarada = state.clasificacionSbs,
            tieneAhorros = state.tieneAhorros,
            montoAhorros = ahorros,
            scoreMatchcredit = scoreResult.score,
            nivelRiesgo = scoreResult.nivelRiesgo,
            ratioEndeudamientoActual = ratio,
            capacidadPagoDisponible = capacidadPago
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                perfilFinancieroRepository.insertar(perfil)
                _uiState.update { it.copy(isLoading = false, guardadoExitoso = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Error al guardar: ${e.message}") }
            }
        }
    }
}