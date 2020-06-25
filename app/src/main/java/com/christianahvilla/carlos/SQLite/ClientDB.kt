package com.christianahvilla.carlos.SQLite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.christianahvilla.carlos.Models.Client

class ClientDB {

    fun saveClient(client: Client, setDB: SetDB){
        val db: SQLiteDatabase = setDB.writableDatabase
        val values = ContentValues()
        println(client)
        values.put(Const.CLIENT, client.client)
        values.put(Const.DOMAIN, client.domain)
        values.put(Const.PRICE, client.price.toString())
        db.insert(Const.CLIENTS_TABLE, null,values)
        db.close()
    }

    fun getClients(setDB: SetDB): ArrayList<Client>{
        val listCustomer = ArrayList<Client>()
        val db = setDB.writableDatabase
        val cursor = db.rawQuery(Queries.GET_CLIENTS, null)
        if(cursor.moveToFirst()){
            do{
                val customer = Client(
                    cursor.getInt(cursor.getColumnIndex(Const.ID)),
                    cursor.getString(cursor.getColumnIndex(Const.CLIENT)),
                    cursor.getString(cursor.getColumnIndex(Const.DOMAIN)),
                    cursor.getInt(cursor.getColumnIndex(Const.PRICE))
                )
                listCustomer.add(customer)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listCustomer
    }

    fun delClient(client: Client, setDB: SetDB){
        val db = setDB.writableDatabase

        db.delete(Const.CLIENTS_TABLE, "id=?", arrayOf(client.id.toString()))

        db.close()
    }
}