package com.example.matchcredit.data.dto

data class ResultadoGuardadoDetalle(
    val resultadoId: Int,
    val usuarioId: Int,
    val tipoPrestamoId: Int,
    val productoId: Int,

    val nombreProducto: String,
    val nombreBanco: String,

    val montoSolicitado: Double,
    val plazoMeses: Int,
    val fechaSimulacion: Long,

    val scoreUsado: Int,
    val nivelRiesgoUsado: String,

    val cumpleFiltros: Boolean,
    val cumpleCapacidadPago: Boolean,
    val motivosExclusion: String,

    val teaUsadaPct: Double,
    val temCalculada: Double,
    val cuotaBase: Double,
    val seguroMensual: Double,
    val cuotaEstimada: Double,
    val costoTotalEstimado: Double,
    val interesYSeguroTotal: Double,
    val ratioPostCredito: Double,
    val ranking: Int
)