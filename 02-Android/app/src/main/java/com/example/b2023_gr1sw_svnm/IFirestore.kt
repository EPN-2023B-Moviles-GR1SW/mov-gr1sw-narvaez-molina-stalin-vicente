package com.example.b2023_gr1sw_svnm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.Query
import android.widget.ListView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class IFirestore : AppCompatActivity() {
    var query: Query? = null
    val arreglo: ArrayList<ICities> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ifirestore)

        val listView = findViewById<ListView>(R.id.lv_firestore)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        //botones
        val botonDatosPrueba = findViewById<Button>(R.id.btn_fs_datos_prueba)
        val botonOrderby = findViewById<Button>(R.id.btn_fs_order_by)
        val botonODoc = findViewById<Button>(R.id.btn_fs_odoc)
        val botonIndComp = findViewById<Button>(R.id.btn_fs_ind_comp)
        val botonCrear = findViewById<Button>(R.id.btn_fs_crear)
        val botonEliminar = findViewById<Button>(R.id.btn_fs_eliminar)
        val botonEPaginar = findViewById<Button>(R.id.btn_fs_epaginar)
        val botonPaginar = findViewById<Button>(R.id.btn_fs_paginar)

        botonDatosPrueba
            .setOnClickListener {
                crearDatosPrueba()
            }
        botonOrderby
            .setOnClickListener {
                consultarConOrderBy(adaptador)
            }
        botonODoc
            .setOnClickListener {
                consultarDocumento(adaptador)
            }
        botonIndComp
            .setOnClickListener {

            }
        botonCrear
            .setOnClickListener {

            }
        botonEliminar
            .setOnClickListener {

            }
        botonEPaginar
            .setOnClickListener {

            }
        botonPaginar
            .setOnClickListener {

            }
    }

    fun consultarDocumento(
        adaptador: ArrayAdapter<ICities>
    ) {
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico
            .document("BJ")
            .get()
            .addOnSuccessListener {
                arreglo
                    .add(
                        ICities(
                            it.data?.get("name") as String?,
                            it.data?.get("state") as String?,
                            it.data?.get("country") as String?,
                            it.data?.get("capital") as Boolean?,
                            it.data?.get("population") as Long?,
                            it.data?.get("regions") as ArrayList<String>?
                        )
                    )
                adaptador.notifyDataSetChanged()
            }
            .addOnSuccessListener {
                //si algo salio mal
            }


    }

    fun crearDatosPrueba() {
        val db = Firebase.firestore
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal"),
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal"),
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast"),
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu"),
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei"),
        )
        cities.document("BJ").set(data5)


    }

    fun consultarConOrderBy(
        adaptador: ArrayAdapter<ICities>
    ) {
        val db = Firebase.firestore
        val citiesRefUnico = db.collection("cities")
        limpiarArreglo()
        adaptador.notifyDataSetChanged()
        citiesRefUnico
            .orderBy("population", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                //it eso que llegue
                for (ciudad in it) {
                    ciudad.id
                    anadirAArregloCiudad(ciudad)
                }
                adaptador.notifyDataSetChanged()


            }
            .addOnSuccessListener {
                //errores
            }


    }

    fun limpiarArreglo() {
        arreglo.clear()
    }

    fun anadirAArregloCiudad(
        ciudad: QueryDocumentSnapshot
    ) {
        val nuevaCiudad = ICities(
            ciudad.data.get("name") as String?,
            ciudad.data.get("state") as String?,
            ciudad.data.get("country") as String?,
            ciudad.data.get("capital") as Boolean?,
            ciudad.data.get("population") as Long?,
            ciudad.data.get("regions") as ArrayList<String>?
        )
        arreglo.add(nuevaCiudad)
    }
}