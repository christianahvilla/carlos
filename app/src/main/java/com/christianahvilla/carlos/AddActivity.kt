package com.christianahvilla.carlos

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.webkit.URLUtil
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.christianahvilla.carlos.Helpers.RandomPositions
import com.christianahvilla.carlos.Models.Client
import com.christianahvilla.carlos.SQLite.ClientDB
import com.christianahvilla.carlos.SQLite.SetDB
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_add.*
import java.net.MalformedURLException
import java.net.URL


class AddActivity : AppCompatActivity() {

    private lateinit var db: SetDB
    var selectedKind = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        db = SetDB(this@AddActivity)

        bt_add_save.setOnClickListener {
            saveClient()
        }

        val languages = resources.getStringArray(R.array.Languages)

        if (sp_add_kind != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            sp_add_kind.adapter = adapter

            sp_add_kind.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedKind = languages[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

    }

    private fun validData(): Boolean{
        return isValid(et_add_domain.text.toString()) && et_add_name_client.text.isNotBlank() && et_add_price.text.isNotBlank() && selectedKind.isNotBlank()
    }

    private fun isValid(urlString: String): Boolean {
        try {
            val url = URL(urlString)
            return URLUtil.isValidUrl(url.toString()) && Patterns.WEB_URL.matcher(url.toString()).matches()
        } catch (e: MalformedURLException) {
        }
        return false
    }

    private fun fakePosition(): LatLng? {
        val morelia = LatLng(19.702864, -101.193536)
        val randomPositions = RandomPositions()
        return randomPositions.getRandomLocation(morelia, 1000)
    }

    private fun saveClient() {
        if (validData()) {
            val clientDB = ClientDB()
            val client = Client(
                0,
                et_add_name_client.text.toString(),
                et_add_domain.text.toString(),
                Integer.parseInt(et_add_price.text.toString()),
                fakePosition()!!.latitude.toString(),
                fakePosition()!!.longitude.toString(),
                selectedKind
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