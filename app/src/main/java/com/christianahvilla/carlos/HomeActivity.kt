package com.christianahvilla.carlos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.christianahvilla.carlos.Adapters.HomeAdapter
import com.christianahvilla.carlos.Models.Client
import com.christianahvilla.carlos.SQLite.ClientDB
import com.christianahvilla.carlos.SQLite.SetDB
import com.christianahvilla.carlos.SQLite.UserDB
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var db: SetDB

    //Variables for list
    private var clients: ArrayList<Client> = ArrayList()
    private var adapter: HomeAdapter? = null

    //Variables for database
    private var clientDB: ClientDB = ClientDB()
    private var userDB: UserDB = UserDB()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        db = SetDB(this)
        getCustomers()
        createRecycler()

        fb_add_client.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddActivity::class.java)
            startActivity(intent)
            finish()
        }

        fb_logout.setOnClickListener {
            logout()
        }
    }

    private fun logout(){
        userDB.logout(db)
        val intent = Intent(this@HomeActivity, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun createRecycler(){
        val lim = LinearLayoutManager(this)
        lim.orientation = LinearLayoutManager.VERTICAL
        client_list.layoutManager = lim
        adapter = HomeAdapter(clients, this@HomeActivity)
        client_list.adapter = adapter
    }

    private fun getCustomers() {
        this.clients = clientDB.getClients(db)
    }
}