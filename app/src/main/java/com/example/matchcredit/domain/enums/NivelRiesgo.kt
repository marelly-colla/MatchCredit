package com.example.matchcredit.domain.enums

enum class NivelRiesgo(
    val categoria: String,
    val interpretacion: String
) {
    ALTA_COMPATIBILIDAD(
        categoria = "Alta compatibilidad",
        interpretacion = "El usuario tiene mejores condiciones financieras para comparar opciones."
    ),

    COMPATIBILIDAD_MEDIA(
        categoria = "Compatibilidad media",
        interpretacion = "El usuario podría calificar en algunas opciones, pero debe revisar costos."
    ),

    COMPATIBILIDAD_BAJA(
        categoria = "Compatibilidad baja",
        interpretacion = "El usuario puede estar ajustado; la app debe advertir sobre capacidad de pago."
    ),

    RIESGO_ALTO(
        categoria = "Riesgo alto",
        interpretacion = "No se recomienda asumir una nueva deuda sin mejorar ingresos, reducir gastos o pagar deudas."
    )
}