package com.christianahvilla.carlos.SQLite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.christianahvilla.carlos.Helpers.Encrypt
import com.christianahvilla.carlos.Models.Client
import com.christianahvilla.carlos.Models.User

class UserDB(): Encrypt() {

    fun saveUser(user: User, setDB: SetDB){
        val db: SQLiteDatabase = setDB.writableDatabase
        val values = ContentValues()
        values.put(Const.NAME, user.name)
        values.put(Const.EMAIL, user.email)
        values.put(Const.LOGGED, user.logged)
        values.put(Const.PASSWORD, this.encrypt(user.password))
        db.insert(Const.USERS_TABLE, null,values)
        db.close()
    }

    fun findUser(email:String, password: String?,setDB: SetDB): User? {
        val db = setDB.writableDatabase
        val query = Queries.GET_USER + "password == '$password' AND email == '$email'"
        val cursor = db.rawQuery(query, null)
        if(cursor.moveToFirst()){
            val user = User(
                cursor.getInt(cursor.getColumnIndex(Const.ID)),
                cursor.getString(cursor.getColumnIndex(Const.NAME)),
                cursor.getString(cursor.getColumnIndex(Const.EMAIL)),
                "",
                cursor.getInt(cursor.getColumnIndex(Const.LOGGED))
            )
            updateUser(user, setDB)
            cursor.close()
            db.close()
            return user
        }
        cursor.close()
        db.close()
        return null
    }

    private fun updateUser(user: User, setDB: SetDB){
        val db = setDB.writableDatabase
        val values = ContentValues()
        values.put(Const.LOGGED, 1)
        db.update(Const.USERS_TABLE, values, "id = ?", arrayOf(user.id.toString()))
        db.close()
    }

    fun loggedUser(setDB: SetDB): User?{
        val db = setDB.writableDatabase
        val cursor = db.rawQuery(Queries.LOGGED_USER, null)
        if(cursor.moveToFirst()){
            val user = User(
                cursor.getInt(cursor.getColumnIndex(Const.ID)),
                cursor.getString(cursor.getColumnIndex(Const.NAME)),
                cursor.getString(cursor.getColumnIndex(Const.EMAIL)),
                "",
                cursor.getInt(cursor.getColumnIndex(Const.LOGGED))
            )
            cursor.close()
            db.close()
            return user
        }
        cursor.close()
        db.close()
        return null
    }

    fun logout(setDB: SetDB){
        val db = setDB.writableDatabase
        val values = ContentValues()
        values.put(Const.LOGGED, 0)
        db.update(Const.USERS_TABLE, values, null, null)
        db.close()
    }

}