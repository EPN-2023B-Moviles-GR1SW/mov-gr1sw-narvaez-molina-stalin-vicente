package com.example.b2023_gr1sw_svnm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion
    (
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<
        FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder
        >() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(
        view
    ) {
        val nombreTextView : TextView
        val descripcionTexView : TextView
        val likesTextView : TextView
        val accionButton : Button
        var numeroLikes = 0
        init {
            nombreTextView = view. findViewById(R.id.tv_nombre)
            descripcionTexView = view. findViewById(R.id.tv_descripcion)
            likesTextView = view. findViewById(R.id.tv_like)
            accionButton = view. findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener{anadirLike()}

        }
        fun anadirLike(){
            numeroLikes = numeroLikes +1
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarLikes()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder {
       val itemView = LayoutInflater
           .from(parent.context)
           .inflate(
               R.layout.recycler_view_vista,
               parent,
               false
           )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder,
        position: Int
    ) {
       val entrenadorActual = this.lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.descripcionTexView.text = entrenadorActual.descripcion
        holder.likesTextView.text = "0"
        holder.accionButton.text = "ID: ${entrenadorActual.id}" + "Nombre: ${entrenadorActual.nombre}"
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

}