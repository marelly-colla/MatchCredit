package com.example.matchcredit.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class MatchCreditDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}