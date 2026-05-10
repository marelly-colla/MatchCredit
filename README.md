# MatchCredit 📱💳

> **MatchCredit** es una aplicación móvil Fintech de inteligencia financiera diseñada para facilitar la búsqueda, comparación y selección de productos crediticios en Perú. 

A través del análisis del perfil financiero del usuario, la aplicación calcula el Costo Efectivo Total (TCEA) en tiempo real, filtra las opciones viables según la elegibilidad y fomenta una toma de decisiones transparente y segura.

---

## ⚠️ El Problema
El mercado de créditos de consumo en Perú carece de transparencia. Las entidades financieras suelen destacar la Tasa Efectiva Anual (TEA), ocultando los seguros, comisiones y gastos adicionales que componen la **TCEA**. Esto, sumado a los bajos niveles de educación financiera, provoca que los usuarios:
* Adquieran créditos con condiciones desfavorables.
* Realicen múltiples consultas que afectan su historial crediticio.
* Estén expuestos a esquemas informales y aplicativos fraudulentos.

## 💡 La Solución
MatchCredit centraliza la información financiera y utiliza un sistema de análisis basado en el perfil del usuario para mostrar **únicamente las opciones crediticias viables**. 

### Funcionalidades Principales:
- 📊 **Perfilamiento financiero inteligente:** Registro de ingresos, gastos, situación laboral e historial crediticio.
- 🎯 **Algoritmo de elegibilidad:** Emparejamiento del perfil del usuario con los criterios de aprobación de diversas entidades para reducir el rechazo.
- 🔍 **Comparación transparente de costos:** Cálculo automático de la TCEA, desglosando intereses, seguros y comisiones.
- 📑 **Centralización de requisitos:** Visualización previa de los documentos necesarios antes de iniciar una solicitud formal.

---

## 🎨 Diseño y UI/UX

**Pantallas principales:**
1. Registro e inicio de sesión.
2. Dashboard / Página principal.
3. Perfil financiero del usuario.
4. Comparador de tasas y detalle de producto.

---

## 🛠️ Tecnologías y Arquitectura

El desarrollo de la aplicación es nativo para el ecosistema Android, estructurado bajo el patrón de arquitectura **MVVM (Model-View-ViewModel)**.

* **Lenguaje:** Kotlin
* **IDE:** Android Studio
* **Interfaz Gráfica:** Jetpack Compose & Material Design 3
* **Navegación:** Navigation Compose
* **Base de Datos Local:** Room Database
* **Manejo de Estado:** ViewModel y LiveData

---

## 🔄 Flujo de Trabajo y Git

Este proyecto es desarrollado de manera colaborativa mediante un enfoque estructurado de control de versiones:
* **Ramas funcionales:** Cada nueva funcionalidad (autenticación, UI, base de datos) se desarrolla en ramas independientes (`feature/nombre-de-la-funcion`).
* **Integración:** Una vez finalizadas y probadas, las ramas funcionales se unen a la rama `develop` mediante **Pull Requests**.
* **Trazabilidad:** Uso de commits descriptivos para facilitar el seguimiento de los avances.

---

## 👥 Equipo de Desarrollo

* **[Massiel Colla Cervantes]** - *Desarrollo Android & UI/UX*
* **[Stephany Toribio Alvarado]** - *Desarrollo Android & Arquitectura*

---

## 🚀 Estado del Proyecto
*Avance parcial:* 
- [x] Definición de requerimientos y arquitectura.
- [x] Diseño de interfaces gráficas en Figma.
- [ ] Configuración del proyecto en Android Studio.
- [ ] Implementación de navegación y pantallas base.