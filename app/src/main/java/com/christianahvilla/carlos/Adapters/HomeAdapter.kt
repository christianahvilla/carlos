package com.christianahvilla.carlos.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.christianahvilla.carlos.Models.Client
import com.christianahvilla.carlos.R

class HomeAdapter(private var clients: ArrayList<Client>, private val context: Context):  RecyclerView.Adapter<ViewHolder>() {

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
        holder.price.text = "$${clients[position].price.toString()}"
    }
}

class ViewHolder (view: View): RecyclerView.ViewHolder(view){
    val client: TextView = view.findViewById(R.id.tv_list_client)
    val domain: TextView = view.findViewById(R.id.tv_list_domain)
    val price: TextView = view.findViewById(R.id.tv_list_price)
}