package com.christianahvilla.carlos.SQLite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SetDB(ctx: Context) : SQLiteOpenHelper(ctx, "hosting", null, 1) {

    companion object {
        private var instance: SetDB? = null

        @Synchronized
        fun getInstance(ctx: Context): SetDB {
            if (instance == null) {
                instance = SetDB(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Queries.CLIENTS_TABLE)
        db.execSQL(Queries.USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}

// Access property for Context
val Context.database: SetDB
    get() = SetDB.getInstance(applicationContext)