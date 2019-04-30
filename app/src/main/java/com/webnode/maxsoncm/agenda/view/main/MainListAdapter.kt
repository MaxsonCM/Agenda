package com.webnode.maxsoncm.agenda.view.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.webnode.maxsoncm.agenda.R
import com.webnode.maxsoncm.agenda.model.Contato
import kotlinx.android.synthetic.main.contato_item.view.*

class MainListAdapter(
    val context: Context,
    val notas: List<Contato>,
    val clickLista: (Contato) -> Unit/*,
    val clickLixo: (String) -> Unit*/
) :
    RecyclerView.Adapter<MainListAdapter.ContatoViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContatoViewHolder {
        var itemView = LayoutInflater.from(context)
            .inflate(R.layout.contato_item, p0, false)

        return ContatoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    override fun onBindViewHolder(p0: ContatoViewHolder, position: Int) {
        val contato = notas[position]
        p0.bindView(contato, clickLista)
        /*p0.excluir(contato.age_id, clickLixo)*/
    }

    class ContatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView( contato: Contato, clickLista : (Contato) -> Unit) = with(itemView){
            tvId.text = contato.age_id
            tvNome.text = contato.age_nome
            tvEmail.text = contato.age_email
            tvTelefone.text = contato.age_telefone

            setOnClickListener{ clickLista(contato)}
        }
        /*fun excluir( age_id: String, clickLixo: (String) -> Unit){
            itemView.ivExcluir.setOnClickListener{ clickLixo(age_id) }
        }*/
    }

}