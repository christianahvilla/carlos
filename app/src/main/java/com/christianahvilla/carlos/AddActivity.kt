package com.christianahvilla.carlos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.christianahvilla.carlos.Helpers.Encrypt
import com.christianahvilla.carlos.Models.Client
import com.christianahvilla.carlos.Models.User
import com.christianahvilla.carlos.SQLite.ClientDB
import com.christianahvilla.carlos.SQLite.SetDB
import com.christianahvilla.carlos.SQLite.UserDB
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class AddActivity : AppCompatActivity() {

    private lateinit var db: SetDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = SetDB(this@AddActivity)

        bt_add_save.setOnClickListener {
            saveClient()
        }

    }

    private fun validData(): Boolean{
        return et_add_domain.text.isNotBlank() && et_add_name_client.text.isNotBlank() && et_add_price.text.isNotBlank()
    }

    private fun saveClient() {
        if (validData()) {
            val clientDB = ClientDB()
            val client = Client(
                0,
                et_add_name_client.text.toString(),
                et_add_domain.text.toString(),
                Integer.parseInt(et_add_price.text.toString()),
                "puto",
                "puto",
                "puto"
            )
            clientDB.saveClient(client, db)
            val intent = Intent(this@AddActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            Toast.makeText(this@AddActivity, resources.getString(R.string.invalid_data), Toast.LENGTH_SHORT).show()
        }
    }
}