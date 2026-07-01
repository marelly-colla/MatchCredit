package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchcredit.data.repository.BancoRepository
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.ProductoCrediticioRepository
import com.example.matchcredit.domain.calculator.PaymentCalculator
import com.example.matchcredit.domain.calculator.RankingCalculator
import com.example.matchcredit.domain.calculator.ResultadoPrestamoCalculado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ResultadosPrestamoUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val resultados: List<ResultadoPrestamoCalculado> = emptyList()
)

class ResultadosPrestamoViewModel(
    private val usuarioId: Int,
    private val tipoPrestamo: String,
    private val montoSolicitado: Double,
    private val plazoMeses: Int,
    private val perfilFinancieroRepository: PerfilFinancieroRepository,
    private val productoCrediticioRepository: ProductoCrediticioRepository,
    private val bancoRepository: BancoRepository
) : ViewModel() {

    private val paymentCalculator = PaymentCalculator()
    private val rankingCalculator = RankingCalculator()

    private val _uiState = MutableStateFlow(ResultadosPrestamoUiState())
    val uiState: StateFlow<ResultadosPrestamoUiState> = _uiState

    init {
        cargarResultados()
    }

    private fun cargarResultados() {
        viewModelScope.launch {
            try {
                _uiState.value = ResultadosPrestamoUiState(cargando = true)

                val perfil = perfilFinancieroRepository.obtenerPorUsuario(usuarioId)

                if (perfil == null) {
                    _uiState.value = ResultadosPrestamoUiState(
                        cargando = false,
                        error = "Primero debes completar tu perfil financiero."
                    )
                    return@launch
                }

                val tipoPrestamoId = rankingCalculator.obtenerTipoPrestamoId(tipoPrestamo)
                val productos = productoCrediticioRepository.obtenerPorTipo(tipoPrestamoId)

                if (productos.isEmpty()) {
                    _uiState.value = ResultadosPrestamoUiState(
                        cargando = false,
                        error = "No se encontraron productos para este tipo de préstamo."
                    )
                    return@launch
                }

                val resultados = productos.map { producto ->
                    val banco = bancoRepository.obtenerPorId(producto.bancoId)

                    rankingCalculator.evaluarProducto(
                        producto = producto,
                        bancoNombre = banco?.nombre ?: "Banco no identificado",
                        perfil = perfil,
                        montoSolicitado = montoSolicitado,
                        plazoMeses = plazoMeses,
                        paymentCalculator = paymentCalculator
                    )
                }

                val resultadosOrdenados = rankingCalculator.ordenarResultados(resultados)

                _uiState.value = ResultadosPrestamoUiState(
                    cargando = false,
                    resultados = resultadosOrdenados
                )
            } catch (e: Exception) {
                _uiState.value = ResultadosPrestamoUiState(
                    cargando = false,
                    error = e.message ?: "Ocurrió un error al calcular los resultados."
                )
            }
        }
    }
}