package com.christianahvilla.carlos.SQLite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.christianahvilla.carlos.Models.Client

class ClientDB {

    fun saveClient(client: Client, setDB: SetDB){
        val db: SQLiteDatabase = setDB.writableDatabase
        val values = ContentValues()
        values.put(Const.CLIENT, client.client)
        values.put(Const.DOMAIN, client.domain)
        values.put(Const.PRICE, client.price.toString())
        values.put(Const.LAT, client.lat)
        values.put(Const.LON, client.lon)
        values.put(Const.KIND, client.kind)
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
                    cursor.getInt(cursor.getColumnIndex(Const.PRICE)),
                    cursor.getString(cursor.getColumnIndex(Const.LAT)),
                    cursor.getString(cursor.getColumnIndex(Const.LON)),
                    cursor.getString(cursor.getColumnIndex(Const.KIND))
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

    fun getDetail(client: Client, setDB: SetDB): Client?{
        val db = setDB.writableDatabase
        val cursor = db.rawQuery(Queries.GET_CLIENT_DATA + client.id, null)
        if(cursor.moveToFirst()){
            val client = Client(
                cursor.getInt(cursor.getColumnIndex(Const.ID)),
                cursor.getString(cursor.getColumnIndex(Const.CLIENT)),
                cursor.getString(cursor.getColumnIndex(Const.DOMAIN)),
                cursor.getInt(cursor.getColumnIndex(Const.PRICE)),
                cursor.getString(cursor.getColumnIndex(Const.LAT)),
                cursor.getString(cursor.getColumnIndex(Const.LON)),
                cursor.getString(cursor.getColumnIndex(Const.KIND))
            )
            cursor.close()
            db.close()
            return client
        }
        cursor.close()
        db.close()
        return null
    }

}