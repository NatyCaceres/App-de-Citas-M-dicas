package com.example.ejercicio2bd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitaAdapter(
    private val context: Context,
    private var listaCitas: List<cita>,// Lista de citas a mostrar
    private val doctores: List<doctor>,
    private val consultorios: List<consultorio>,
    private val horarios: List<horario>,
    private val onEditar: (cita) -> Unit,// Funcion que se ejecuta al presionar "Editar", no devulve nada
    private val onEliminar: (cita) -> Unit
) : RecyclerView.Adapter<CitaAdapter.CitaViewHolder>() { //conecta datos con un RecyclerView

    // ViewHolder que asocia vistas de item_cita.xml
    inner class CitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDoctor: TextView = itemView.findViewById(R.id.tvDoctor)
        val txtConsultorio: TextView = itemView.findViewById(R.id.tvConsultorio)
        val txtHorario: TextView = itemView.findViewById(R.id.tvHorario)
        val txtMotivo: TextView = itemView.findViewById(R.id.tvMotivo)
        val btnEditar: Button = itemView.findViewById(R.id.btnActualizar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
    }

    // Se crea el layout del ítem (item_cita.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }
    // Devuelve el numero de citas a mostrar
    override fun getItemCount(): Int = listaCitas.size

    // Asocia los datos con cada vista de la cita
    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listaCitas[position]

        // Buscar doctor según id_doctor de la cita,devuelve el primer elemento que cumpla la condicio o null
        val doctor = doctores.find { it.id_doctor == cita.id_doctor }
        val consultorio = consultorios.find { it.id_consultorio == cita.id_consultorio }
        val horario = horarios.find { it.id_horario == cita.id_horario }

        // accese al textView guardado en el ViewHolder
        holder.txtDoctor.text = "Doctor: ${doctor?.nombre ?: "Desconocido"}"
        holder.txtConsultorio.text = "Consultorio: ${consultorio?.nombre ?: "Desconocido"}"
        holder.txtHorario.text = "Horario: ${horario?.dia} - ${horario?.hora_inicio} a ${horario?.hora_fin}"
        holder.txtMotivo.text = "Motivo: ${cita.motivo}"

        // Listener para editar la cita (se ejecuta la función que recibimos)
        holder.btnEditar.setOnClickListener {
            onEditar(cita)
        }
        holder.btnEliminar.setOnClickListener {
            onEliminar(cita)
        }
    }
}