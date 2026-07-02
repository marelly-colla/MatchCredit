package com.example.matchcredit.data.dto

data class ResultadoGuardadoResumen(
    val resultadoId: Int,
    val productoId: Int,
    val ranking: Int,

    val montoSolicitado: Double,
    val plazoMeses: Int,
    val fechaSimulacion: Long,

    val scoreUsado: Int,
    val nivelRiesgoUsado: String,

    val teaUsadaPct: Double,
    val cuotaEstimada: Double,
    val costoTotalEstimado: Double,

    val nombreProducto: String,
    val nombreBanco: String
)