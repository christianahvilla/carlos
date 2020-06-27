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

    var selectedKind = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        bt_add_next.setOnClickListener {
            nextStep()
        }

        val languages = resources.getStringArray(R.array.Kinds)

        if (sp_add_kind != null) {
            val adapter = ArrayAdapter(this,
                R.layout.layout_options, R.id.tv_option, languages)
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

    private fun nextStep() {
        if (validData()) {
            val intent = Intent(this@AddActivity, AddressActivity::class.java)
            intent.putExtra("client", et_add_name_client.text.toString())
            intent.putExtra("kind", selectedKind)
            intent.putExtra("domain", et_add_domain.text.toString())
            intent.putExtra("price", Integer.parseInt(et_add_price.text.toString()))
            println(et_add_price.text.toString())
            startActivity(intent)
            finish()
        }
        else {
            Toast.makeText(this@AddActivity, resources.getString(R.string.invalid_data), Toast.LENGTH_SHORT).show()
        }
    }
}