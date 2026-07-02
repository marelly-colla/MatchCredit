package com.example.matchcredit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.matchcredit.data.local.entities.PerfilFinanciero

@Dao
interface PerfilFinancieroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(perfil: PerfilFinanciero)

    @Update
    suspend fun actualizar(perfil: PerfilFinanciero)

    @Query("SELECT * FROM perfiles_financieros WHERE usuarioId = :usuarioId LIMIT 1")
    suspend fun obtenerPorUsuario(usuarioId: Int): PerfilFinanciero?
}