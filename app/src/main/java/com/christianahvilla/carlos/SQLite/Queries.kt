package com.christianahvilla.carlos.SQLite

class Queries {
    companion object{
        const val CLIENTS_TABLE =
            "CREATE TABLE `clients` (\n" +
                    "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t`client`\tTEXT,\n" +
                    "\t`domain`\tTEXT,\n" +
                    "\t`price`\tINTEGER\n" +
                    ");"


        const val USERS_TABLE ="CREATE TABLE `users` (\n" +
                "\t`id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`name`\tTEXT,\n" +
                "\t`email`\tTEXT,\n" +
                "\t`password`\tTEXT,\n" +
                "\t`logged`\tINTEGER\n" +
                ");"

        const val GET_CLIENTS = "SELECT * FROM clients ORDER BY client"
        const val GET_USER = "SELECT * FROM users where "
        const val LOGGED_USER = "SELECT * FROM users where logged = 1"
        const val GET_CLIENT_DATA = "SELECT * FROM clients where id = "
    }
}