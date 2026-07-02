package com.example.matchcredit.domain.calculator

enum class PrioridadRecomendacion {
    ALTA,
    MEDIA,
    BAJA
}

data class RecomendacionPerfil(
    val titulo: String,
    val descripcion: String,
    val prioridad: PrioridadRecomendacion
)

object RecomendacionPerfilCalculator {

    fun generar(
        score: Int?,
        ratioEndeudamientoActual: Double?,
        capacidadPagoDisponible: Double?,
        ingresoMensual: Double?,
        gastosMensuales: Double?,
        cuotaMensualDeudas: Double?,
        antiguedadTrabajandoMeses: Int?,
        tieneAhorros: Boolean?,
        montoAhorros: Double?
    ): List<RecomendacionPerfil> {
        if (
            score == null ||
            ratioEndeudamientoActual == null ||
            capacidadPagoDisponible == null ||
            ingresoMensual == null ||
            ingresoMensual <= 0.0
        ) {
            return listOf(
                RecomendacionPerfil(
                    titulo = "Completa tu perfil financiero",
                    descripcion = "Registra tus ingresos, gastos, deudas y datos laborales para recibir recomendaciones personalizadas.",
                    prioridad = PrioridadRecomendacion.ALTA
                )
            )
        }

        val recomendaciones = mutableListOf<RecomendacionPerfil>()

        if (score < 60) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Mejora tu perfil antes de solicitar",
                    descripcion = "Tu Score MatchCredit es bajo. Antes de pedir un préstamo, revisa tus deudas, gastos y capacidad de pago.",
                    prioridad = PrioridadRecomendacion.ALTA
                )
            )
        }

        if (ratioEndeudamientoActual > 0.60) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Reduce tu nivel de deuda",
                    descripcion = "Tu ratio de endeudamiento supera el rango saludable. Una nueva cuota podría afectar tu presupuesto mensual.",
                    prioridad = PrioridadRecomendacion.ALTA
                )
            )
        } else if (ratioEndeudamientoActual > 0.40) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Controla tus cuotas mensuales",
                    descripcion = "Tu deuda aún puede ser manejable, pero conviene evitar préstamos que eleven demasiado tu ratio final.",
                    prioridad = PrioridadRecomendacion.MEDIA
                )
            )
        }

        if (capacidadPagoDisponible <= 0.0) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Recupera capacidad de pago",
                    descripcion = "Tus gastos y deudas consumen gran parte de tus ingresos. Antes de solicitar crédito, intenta liberar presupuesto mensual.",
                    prioridad = PrioridadRecomendacion.ALTA
                )
            )
        }

        val gastos = gastosMensuales ?: 0.0
        val porcentajeGastos = gastos / ingresoMensual

        if (porcentajeGastos > 0.50) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Revisa tus gastos fijos",
                    descripcion = "Tus gastos representan más de la mitad de tus ingresos. Reducirlos puede mejorar tu capacidad para asumir una cuota.",
                    prioridad = PrioridadRecomendacion.MEDIA
                )
            )
        }

        val cuotas = cuotaMensualDeudas ?: 0.0
        val porcentajeCuotas = cuotas / ingresoMensual

        if (porcentajeCuotas > 0.30) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Disminuye tus cuotas actuales",
                    descripcion = "Tus cuotas de deuda actuales ocupan una parte importante de tus ingresos. Reducirlas puede mejorar tu compatibilidad crediticia.",
                    prioridad = PrioridadRecomendacion.ALTA
                )
            )
        }

        val antiguedad = antiguedadTrabajandoMeses ?: 0

        if (antiguedad < 6) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Fortalece tu estabilidad laboral",
                    descripcion = "Algunos productos crediticios solicitan una antigüedad laboral mínima. Tener más meses de estabilidad puede mejorar tus opciones.",
                    prioridad = PrioridadRecomendacion.MEDIA
                )
            )
        }

        val ahorros = montoAhorros ?: 0.0
        val ahorroMinimoSugerido = ingresoMensual * 0.10

        if (tieneAhorros != true || ahorros < ahorroMinimoSugerido) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Construye un fondo de ahorro",
                    descripcion = "Contar con ahorros puede ayudarte a enfrentar imprevistos y mejorar tu perfil financiero.",
                    prioridad = PrioridadRecomendacion.MEDIA
                )
            )
        }

        if (recomendaciones.isEmpty()) {
            recomendaciones.add(
                RecomendacionPerfil(
                    titulo = "Mantén tu buen perfil",
                    descripcion = "Tu perfil financiero está bien encaminado. Sigue comparando opciones y evita asumir cuotas innecesarias.",
                    prioridad = PrioridadRecomendacion.BAJA
                )
            )
        }

        return recomendaciones
            .distinctBy { it.titulo }
            .sortedBy { prioridadOrden(it.prioridad) }
            .take(4)
    }

    private fun prioridadOrden(
        prioridad: PrioridadRecomendacion
    ): Int {
        return when (prioridad) {
            PrioridadRecomendacion.ALTA -> 1
            PrioridadRecomendacion.MEDIA -> 2
            PrioridadRecomendacion.BAJA -> 3
        }
    }
}