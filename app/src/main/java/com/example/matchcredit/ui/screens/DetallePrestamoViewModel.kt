package com.example.matchcredit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchcredit.data.local.entities.ResultadoGuardado
import com.example.matchcredit.data.repository.BancoRepository
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.ProductoCrediticioRepository
import com.example.matchcredit.data.repository.ResultadoGuardadoRepository
import com.example.matchcredit.domain.calculator.PaymentCalculator
import com.example.matchcredit.domain.calculator.RankingCalculator
import com.example.matchcredit.domain.calculator.ResultadoPrestamoCalculado
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DetallePrestamoUiState(
    val cargando: Boolean = true,
    val error: String? = null,
    val resultado: ResultadoPrestamoCalculado? = null,
    val guardando: Boolean = false,
    val guardadoExitoso: Boolean = false,
    val mensaje: String? = null
)

class DetallePrestamoViewModel(
    private val usuarioId: Int,
    private val productoId: Int,
    private val montoSolicitado: Double,
    private val plazoMeses: Int,
    private val ranking: Int,
    private val perfilFinancieroRepository: PerfilFinancieroRepository,
    private val productoCrediticioRepository: ProductoCrediticioRepository,
    private val bancoRepository: BancoRepository,
    private val resultadoGuardadoRepository: ResultadoGuardadoRepository
) : ViewModel() {

    private val paymentCalculator = PaymentCalculator()
    private val rankingCalculator = RankingCalculator()

    private val _uiState = MutableStateFlow(DetallePrestamoUiState())
    val uiState: StateFlow<DetallePrestamoUiState> = _uiState

    init {
        cargarDetalle()
    }

    private fun cargarDetalle() {
        viewModelScope.launch {
            try {
                _uiState.value = DetallePrestamoUiState(
                    cargando = true
                )

                val perfil = perfilFinancieroRepository.obtenerPorUsuario(usuarioId)

                if (perfil == null) {
                    _uiState.value = DetallePrestamoUiState(
                        cargando = false,
                        error = "Primero debes completar tu perfil financiero."
                    )
                    return@launch
                }

                val producto = productoCrediticioRepository.obtenerPorId(productoId)

                if (producto == null) {
                    _uiState.value = DetallePrestamoUiState(
                        cargando = false,
                        error = "No se encontró el producto seleccionado."
                    )
                    return@launch
                }

                val banco = bancoRepository.obtenerPorId(producto.bancoId)

                val resultado = rankingCalculator.evaluarProducto(
                    producto = producto,
                    bancoNombre = banco?.nombre ?: "Banco no identificado",
                    perfil = perfil,
                    montoSolicitado = montoSolicitado,
                    plazoMeses = plazoMeses,
                    paymentCalculator = paymentCalculator
                ).copy(
                    ranking = ranking
                )

                _uiState.value = DetallePrestamoUiState(
                    cargando = false,
                    resultado = resultado
                )
            } catch (e: Exception) {
                _uiState.value = DetallePrestamoUiState(
                    cargando = false,
                    error = e.message ?: "Ocurrió un error al cargar el detalle del préstamo."
                )
            }
        }
    }

    fun guardarSimulacion() {
        viewModelScope.launch {
            try {
                val estadoActual = _uiState.value
                val resultado = estadoActual.resultado

                if (resultado == null) {
                    _uiState.value = estadoActual.copy(
                        guardando = false,
                        mensaje = "No se pudo guardar porque no existe un resultado calculado."
                    )
                    return@launch
                }

                val perfil = perfilFinancieroRepository.obtenerPorUsuario(usuarioId)

                if (perfil == null) {
                    _uiState.value = estadoActual.copy(
                        guardando = false,
                        mensaje = "No se pudo guardar porque falta el perfil financiero."
                    )
                    return@launch
                }

                _uiState.value = estadoActual.copy(
                    guardando = true,
                    mensaje = null
                )

                val resultadoGuardado = ResultadoGuardado(
                    usuarioId = usuarioId,
                    tipoPrestamoId = resultado.producto.tipoPrestamoId,
                    montoSolicitado = montoSolicitado,
                    plazoMeses = plazoMeses,
                    fechaSimulacion = System.currentTimeMillis(),

                    scoreUsado = perfil.scoreMatchcredit ?: 0,
                    nivelRiesgoUsado = perfil.nivelRiesgo?.categoria ?: "Sin nivel",

                    productoId = resultado.producto.productoId,

                    cumpleFiltros = resultado.cumpleFiltros,
                    cumpleCapacidadPago = resultado.cumpleCapacidadPago,
                    motivosExclusion = resultado.motivosExclusion.joinToString("; "),

                    teaUsadaPct = resultado.paymentResult.teaUsadaPct,
                    temCalculada = resultado.paymentResult.temCalculada,
                    cuotaBase = resultado.paymentResult.cuotaBase,
                    seguroMensual = resultado.paymentResult.seguroMensual,
                    cuotaEstimada = resultado.paymentResult.cuotaEstimada,
                    costoTotalEstimado = resultado.paymentResult.costoTotalEstimado,
                    interesYSeguroTotal = resultado.paymentResult.interesYSeguroTotal,
                    ratioPostCredito = resultado.ratioPostCredito,
                    ranking = resultado.ranking ?: ranking
                )

                resultadoGuardadoRepository.guardar(resultadoGuardado)

                _uiState.value = _uiState.value.copy(
                    guardando = false,
                    guardadoExitoso = true,
                    mensaje = "Simulación guardada correctamente."
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    guardando = false,
                    guardadoExitoso = false,
                    mensaje = e.message ?: "No se pudo guardar la simulación."
                )
            }
        }
    }
}