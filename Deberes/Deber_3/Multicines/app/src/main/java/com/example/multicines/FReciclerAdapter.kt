package com.example.multicines

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FReciclerAdapter(
    private val contexto: Context,
    private val peliculas: ArrayList<Pelicula>,
    private val recyclerView: RecyclerView

) : RecyclerView.Adapter<
        FReciclerAdapter.MyViewHolder
        >() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(
        view
    ) {

        val nombreTextView: TextView = itemView.findViewById(R.id.rv_title)
        val publicoTextView: TextView = itemView.findViewById(R.id.rv_publico)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FReciclerAdapter.MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_movie_card,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FReciclerAdapter.MyViewHolder,
        position: Int
    ) {
        val peliculaActual = this.peliculas[position]
        holder.nombreTextView.text = peliculaActual.nombre
        holder.publicoTextView.text = peliculaActual.publico
    }

    override fun getItemCount(): Int {
        return peliculas.size
    }
}