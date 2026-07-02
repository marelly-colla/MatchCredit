# MatchCredit

MatchCredit es una aplicación móvil fintech desarrollada en Android que funciona como un simulador y asistente de orientación crediticia. Su objetivo es ayudar al usuario a evaluar su perfil financiero antes de solicitar un préstamo, comparar productos crediticios referenciales y comprender mejor el costo, riesgo y compatibilidad de cada opción.

La aplicación no aprueba créditos, no reemplaza la evaluación oficial de una entidad financiera y no consulta automáticamente la SBS ni bancos en tiempo real. Los resultados mostrados son referenciales y se calculan a partir de los datos ingresados por el usuario y de un catálogo local de productos crediticios.

## Problema que resuelve

Muchas personas que desean solicitar un préstamo deben revisar distintas páginas de bancos, comparar tasas, entender requisitos y calcular cuánto pagarían mensualmente. Este proceso puede ser confuso porque la información financiera suele estar dispersa y no siempre es fácil distinguir entre TEA, cuota, seguro, costo total y requisitos mínimos.

MatchCredit busca reducir esa dificultad mostrando, en una sola aplicación, una orientación clara sobre:

- Qué tan saludable es el perfil financiero del usuario.
- Cuánto podría pagar mensualmente sin sobreendeudarse.
- Qué productos crediticios se ajustan mejor a su consulta.
- Qué cuota estimada tendría cada opción.
- Qué productos cumplen o no cumplen requisitos básicos.
- Qué nivel de riesgo tendría asumir un nuevo préstamo.
- Qué simulaciones desea guardar para revisar después.

## Objetivo del proyecto

Desarrollar una aplicación móvil que permita comparar préstamos de manera referencial según el perfil financiero del usuario, mostrando un ranking de opciones, cuota estimada, costo total, requisitos, advertencias de capacidad de pago y simulaciones guardadas.

## Alcance del proyecto

MatchCredit es un prototipo académico de orientación crediticia. La aplicación trabaja con datos referenciales almacenados en una base de datos local y realiza cálculos estimados para apoyar la toma de decisiones del usuario.

La aplicación no realiza aprobación real de créditos, no consulta centrales de riesgo, no obtiene información oficial de SBS en tiempo real y no garantiza que una entidad financiera apruebe una solicitud. La aprobación final, tasa real y condiciones exactas dependen de cada banco.

## Funcionalidades principales

### Autenticación y usuario

- Registro de usuario.
- Inicio de sesión.
- Almacenamiento local de usuarios mediante Room.
- Asociación del usuario con su perfil financiero y simulaciones.

### Perfil financiero

- Registro de datos financieros y laborales.
- Ingreso mensual.
- Gastos mensuales.
- Deuda total actual.
- Cuota mensual de deudas.
- Tipo de trabajo.
- Antigüedad laboral.
- Clasificación crediticia declarada.
- Ahorros.
- Edad del usuario.

### Score MatchCredit

La app calcula un Score MatchCredit interno de 0 a 100. Este score no es un score SBS oficial, sino una medida referencial de compatibilidad financiera.

El score considera:

- Capacidad de pago actual.
- Ingreso mensual.
- Tipo de trabajo.
- Antigüedad laboral.
- Historial crediticio declarado.
- Ahorros.
- Edad.

Categorías del score:

- 80 a 100: Alta compatibilidad.
- 60 a 79: Compatibilidad media.
- 40 a 59: Compatibilidad baja.
- 0 a 39: Riesgo alto.

### Diagnóstico financiero

La pantalla principal muestra un diagnóstico financiero del usuario según su score, ratio de endeudamiento y capacidad de pago.

El diagnóstico puede ser:

- Saludable.
- Moderado.
- Riesgoso.
- Pendiente si falta información.

También se muestra una recomendación principal para orientar al usuario antes de solicitar un préstamo.

### Monto recomendado y cuota saludable

Antes de comparar préstamos, la app calcula una orientación previa para el usuario:

- Cuota saludable máxima.
- Monto estimado recomendado.
- Plazo usado para la estimación.
- TEA referencial usada según tipo de préstamo.
- Mensaje de recomendación.

Esta funcionalidad ayuda a que el usuario no ingrese un monto sin referencia y tenga una idea aproximada de cuánto podría solicitar sin comprometer demasiado su presupuesto.

### Consulta de préstamo

El usuario puede realizar una consulta ingresando:

- Tipo de préstamo.
- Monto solicitado.
- Plazo en meses.

Tipos de préstamo considerados:

- Personal.
- Hipotecario.
- Vehicular.

### Ranking de productos crediticios

La app evalúa productos crediticios registrados en la base local y genera un ranking según:

- Tipo de préstamo.
- Monto mínimo y máximo.
- Plazo mínimo y máximo.
- Ingreso mínimo requerido.
- Antigüedad laboral mínima.
- Cuota estimada.
- Costo total estimado.
- Ratio post crédito.
- Capacidad de pago del usuario.

Los productos pueden aparecer como:

- Recomendado.
- Revisar capacidad.
- No cumple.

### Detalle del préstamo

Cada resultado del ranking puede abrirse para ver el detalle del producto y de la simulación.

El detalle muestra:

- Banco.
- Producto crediticio.
- Ranking.
- Estado de compatibilidad.
- TEA referencial.
- TEM calculada.
- Cuota base.
- Seguro mensual.
- Cuota estimada.
- Costo total estimado.
- Interés y seguro total.
- Ratio final post crédito.
- Requisitos del producto.
- Observaciones o motivos de exclusión.

### Semáforo de riesgo

En el detalle del préstamo se muestra un semáforo de riesgo según el ratio post crédito:

- Riesgo bajo.
- Riesgo moderado.
- Riesgo alto.

Esto permite que el usuario no solo vea cuánto pagaría, sino también qué tan riesgoso podría ser asumir esa nueva cuota.

### Explicación del resultado

La app explica por qué una opción puede ser recomendable, revisable o no ideal.

La explicación incluye:

- Puntos favorables.
- Aspectos a revisar.
- Mensaje de compatibilidad.
- Advertencias de capacidad de pago.

### Simulaciones guardadas

El usuario puede guardar una simulación desde el detalle del préstamo.

La app almacena:

- Usuario.
- Tipo de préstamo.
- Producto.
- Monto solicitado.
- Plazo.
- Fecha de simulación.
- Score usado.
- Nivel usado.
- Cuota estimada.
- Costo total.
- Ratio post crédito.
- Ranking.
- Motivos de exclusión.

### Historial de simulaciones

La pantalla de historial permite:

- Ver simulaciones guardadas.
- Abrir el detalle guardado.
- Eliminar simulaciones.
- Evitar duplicados.
- Mostrar la fecha y hora de guardado según la zona horaria de Perú.

El detalle guardado es diferente al detalle normal de comparación. Si el usuario entra desde Historial, se muestra la información histórica de la simulación y no aparece el botón de guardar nuevamente.

## Flujo general de la aplicación

1. El usuario se registra o inicia sesión.
2. Completa su perfil financiero.
3. La app calcula el Score MatchCredit.
4. La pantalla principal muestra el score, diagnóstico financiero y recomendación principal.
5. El usuario entra a Comparar.
6. La app muestra una orientación previa con cuota saludable y monto recomendado.
7. El usuario selecciona tipo de préstamo, monto y plazo.
8. La app genera un ranking de productos crediticios.
9. El usuario revisa el detalle de una opción.
10. La app muestra cuota, costo, requisitos, riesgo y explicación del resultado.
11. El usuario puede guardar la simulación.
12. El usuario puede revisar, abrir o eliminar simulaciones desde Historial.

## Fórmulas utilizadas

### Ratio de endeudamiento actual

```text
ratioEndeudamientoActual = (gastosMensuales + cuotaMensualDeudas) / ingresoMensual
capacidadPagoDisponible = ingresoMensual - gastosMensuales - cuotaMensualDeudas
TEM = (1 + TEA / 100)^(1 / 12) - 1
cuotaBase = monto * (TEM * (1 + TEM)^plazo) / ((1 + TEM)^plazo - 1)
seguroMensual = montoSolicitado * (seguroDesgravamenMensualPct / 100)
cuotaEstimada = cuotaBase + seguroMensual + comisionMensual + gastoAdministrativo
costoTotalEstimado = cuotaEstimada * plazoMeses
interesYSeguroTotal = costoTotalEstimado - montoSolicitado
ratioPostCredito = (gastosMensuales + cuotaMensualDeudas + cuotaEstimada) / ingresoMensual
```
