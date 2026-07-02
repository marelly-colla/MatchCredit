package com.example.matchcredit.domain.calculator

data class ExplicacionResultado(
    val titulo: String,
    val resumen: String,
    val puntosFavorables: List<String>,
    val puntosObservados: List<String>
)

object ExplicacionResultadoCalculator {

    fun generar(
        resultado: ResultadoPrestamoCalculado
    ): ExplicacionResultado {
        val puntosFavorables = mutableListOf<String>()
        val puntosObservados = mutableListOf<String>()

        if (resultado.cumpleFiltros) {
            puntosFavorables.add("Cumple los requisitos básicos del producto.")
            puntosFavorables.add("El monto solicitado está dentro del rango permitido.")
            puntosFavorables.add("El plazo solicitado está dentro del rango aceptado.")
        } else {
            puntosObservados.addAll(resultado.motivosExclusion)
        }

        if (resultado.cumpleCapacidadPago) {
            puntosFavorables.add("La cuota estimada es compatible con tu capacidad de pago.")
        } else {
            puntosObservados.add("La cuota estimada puede elevar demasiado tu nivel de endeudamiento.")
        }

        if (resultado.ratioPostCredito <= 0.35) {
            puntosFavorables.add("El ratio final se mantiene en un nivel saludable.")
        } else if (resultado.ratioPostCredito <= 0.60) {
            puntosObservados.add("El ratio final requiere precaución antes de asumir el préstamo.")
        } else {
            puntosObservados.add("El ratio final queda por encima del rango recomendado.")
        }

        if (resultado.esRecomendado) {
            return ExplicacionResultado(
                titulo = "¿Por qué esta opción es recomendable?",
                resumen = "Esta alternativa combina cumplimiento de requisitos, cuota manejable y un nivel de riesgo más favorable.",
                puntosFavorables = puntosFavorables.distinct(),
                puntosObservados = puntosObservados.distinct()
            )
        }

        if (resultado.cumpleFiltros && !resultado.cumpleCapacidadPago) {
            return ExplicacionResultado(
                titulo = "¿Por qué debes revisar esta opción?",
                resumen = "El producto puede cumplir requisitos básicos, pero la cuota debe evaluarse con cuidado según tu capacidad de pago.",
                puntosFavorables = puntosFavorables.distinct(),
                puntosObservados = puntosObservados.distinct()
            )
        }

        return ExplicacionResultado(
            titulo = "¿Por qué esta opción no es ideal?",
            resumen = "Esta alternativa presenta observaciones que pueden limitar su compatibilidad con tu perfil actual.",
            puntosFavorables = puntosFavorables.distinct(),
            puntosObservados = puntosObservados.distinct()
        )
    }
}