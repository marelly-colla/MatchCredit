package com.example.matchcredit.data

import android.content.Context
import com.example.matchcredit.data.local.database.MatchCreditDatabase
import com.example.matchcredit.data.repository.BancoRepository
import com.example.matchcredit.data.repository.PerfilFinancieroRepository
import com.example.matchcredit.data.repository.ProductoCrediticioRepository
import com.example.matchcredit.data.repository.ResultadoGuardadoRepository
import com.example.matchcredit.data.repository.TipoPrestamoRepository
import com.example.matchcredit.data.repository.UsuarioRepository

class AppContainer(context: Context) {

    private val database = MatchCreditDatabase.getDatabase(context)

    val usuarioRepository: UsuarioRepository by lazy {
        UsuarioRepository(database.usuarioDao())
    }

    val perfilFinancieroRepository: PerfilFinancieroRepository by lazy {
        PerfilFinancieroRepository(database.perfilFinancieroDao())
    }

    val bancoRepository: BancoRepository by lazy {
        BancoRepository(database.bancoDao())
    }

    val tipoPrestamoRepository: TipoPrestamoRepository by lazy {
        TipoPrestamoRepository(database.tipoPrestamoDao())
    }

    val productoCrediticioRepository: ProductoCrediticioRepository by lazy {
        ProductoCrediticioRepository(database.productoCrediticioDao())
    }

    val resultadoGuardadoRepository: ResultadoGuardadoRepository by lazy {
        ResultadoGuardadoRepository(database.resultadoGuardadoDao())
    }
}