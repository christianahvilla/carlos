package com.christianahvilla.carlos.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.christianahvilla.carlos.DetailActivity
import com.christianahvilla.carlos.Models.Client
import com.christianahvilla.carlos.R
import com.christianahvilla.carlos.SQLite.ClientDB
import com.christianahvilla.carlos.SQLite.SetDB

class HomeAdapter(private var clients: ArrayList<Client>, private val context: Context, private val setDB: SetDB):  RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.layout_client_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return clients.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.client.text = clients[position].client
        holder.domain.text = clients[position].domain
        holder.kind.text = clients[position].kind
        holder.price.text = "$${clients[position].price.toString()}"

        holder.menu.setOnClickListener {
            dialogDelete(clients[position], position).show()
        }

        holder.layout.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("client", clients[position].client)
            intent.putExtra("lat", clients[position].lat)
            intent.putExtra("lon", clients[position].lon)
            context.startActivity(intent)
        }
    }

    private fun dialogDelete(client: Client, position: Int): AlertDialog.Builder{
        val dialog = AlertDialog.Builder(context)
        
        dialog.setTitle(R.string.dialog_delete)


        dialog.setPositiveButton(R.string.dialog_accept) { _, _ ->
            val clientDB = ClientDB()
            clientDB.delClient(client, setDB)
            clients.removeAt(position)
            notifyDataSetChanged()
        }

        dialog.setNegativeButton(R.string.dialog_cancel) { _, _ ->
        }

        return dialog
    }

}

class ViewHolder (view: View): RecyclerView.ViewHolder(view){
    val client: TextView = view.findViewById(R.id.tv_list_client)
    val domain: TextView = view.findViewById(R.id.tv_list_domain)
    val price: TextView = view.findViewById(R.id.tv_list_price)
    val menu: TextView = view.findViewById(R.id.tv_list_menu)
    val kind: TextView = view.findViewById(R.id.tv_list_kind)
    val layout: ConstraintLayout = view.findViewById(R.id.lo_list)
}