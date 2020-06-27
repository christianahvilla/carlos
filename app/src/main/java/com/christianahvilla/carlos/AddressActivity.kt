package com.christianahvilla.carlos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import com.christianahvilla.carlos.Helpers.RandomPositions
import com.christianahvilla.carlos.Models.Client
import com.christianahvilla.carlos.SQLite.ClientDB
import com.christianahvilla.carlos.SQLite.SetDB
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_address.*
import java.net.MalformedURLException
import java.net.URL

class AddressActivity : AppCompatActivity() {

    private lateinit var db: SetDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        db = SetDB(this@AddressActivity)

        bt_address_save.setOnClickListener {
            saveClient()
        }

    }

    private fun validData(): Boolean{
        return et_address_neighborhood.text.isNotBlank() &&
                et_address_number.text.isNotBlank() &&
                et_address_state.text.isNotBlank() &&
                et_address_street.text.isNotBlank() &&
                et_address_zip_code.text.isNotBlank()
    }

    private fun saveClient() {
        if (validData()) {
            val clientDB = ClientDB()
            val client = Client(
                0,
                intent.getStringExtra("client"),
                intent.getStringExtra("domain"),
                intent.getIntExtra("price", 0),
                et_address_street.text.toString(),
                et_address_number.text.toString(),
                et_address_neighborhood.text.toString(),
                et_address_state.text.toString(),
                et_address_zip_code.text.toString(),
                intent.getStringExtra("kind")
            )
            clientDB.saveClient(client, db)
            val intent = Intent(this@AddressActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(
                this@AddressActivity,
                resources.getString(R.string.invalid_data),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}