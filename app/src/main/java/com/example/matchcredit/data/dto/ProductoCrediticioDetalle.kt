package com.example.matchcredit.data.dto

data class ProductoCrediticioDetalle(
    val productoId: Int,
    val nombreProducto: String,
    val bancoNombre: String,
    val tipoPrestamoNombre: String,
    val minTrabajoMeses: Int,
    val montoMin: Double,
    val montoMax: Double,
    val plazoMinMeses: Int,
    val plazoMaxMeses: Int,
    val ingresoMin: Double,
    val seguroDesgravamenMensualPct: Double,
    val comisionMensual: Double,
    val gastoAdministrativo: Double
)