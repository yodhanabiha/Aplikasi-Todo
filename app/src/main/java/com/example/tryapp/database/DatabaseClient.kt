package com.example.tryapp.database

import android.content.Context
import androidx.room.Room

private const val dbName = "TodolistNabiha0001.db"

object DatabaseClient {
    fun getService (context: Context): DatabaseService{
        return  Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            dbName
        ).build()
    }
}