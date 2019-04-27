package com.webnode.maxsoncm.agenda.repository

import com.webnode.maxsoncm.agenda.api.getContatoAPI
import com.webnode.maxsoncm.agenda.model.Contato
import com.webnode.maxsoncm.agenda.model.ResponseStatus
import retrofit2.Call
import retrofit2.Callback // cuidado no import do Callback - usar o do Retrofit
import retrofit2.Response

class ContatoRepository {

    fun buscar(
        onComplete:(List<Contato>) -> Unit,
        onError: (Throwable?) -> Unit
    ){
        getContatoAPI()
            .getAgenda()
            .enqueue(object : Callback<List<Contato>>  {
                override fun onFailure(call: Call<List<Contato>>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<List<Contato>>, response: Response<List<Contato>>){
                    if (response.isSuccessful){
                        onComplete(response.body()!!)
                    }else{
                        onError(Throwable("Problemas ao Pesquisar Agenda !"))
                    }
                }
            })
    }

    fun buscar(
        onFilter: String,
        onComplete:(List<Contato>) -> Unit,
        onError: (Throwable?) -> Unit
    ){
        getContatoAPI()
            .getAgendaFiltro(onFilter)
            .enqueue(object : Callback<List<Contato>>  {
                override fun onFailure(call: Call<List<Contato>>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<List<Contato>>, response: Response<List<Contato>>){
                    if (response.isSuccessful){
                        onComplete(response.body()!!)
                    }else{
                        onError(Throwable("Problemas ao Pesquisar Agenda !"))
                    }
                }
            })
    }

    fun salvar(contato: Contato,
               onComplete: (Contato) -> Unit,
               onError: (Throwable?) -> Unit ) {
        getContatoAPI()
            .salvar(contato)
            .enqueue(object : Callback<Contato>{
                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                    if (response.isSuccessful){
                        onComplete(response.body()!!)
                    }else{
                        onError(Throwable("Problemas ao Salvar Cantato !"))
                    }
                }
            })
    }


    fun excluir(id: String,
               onComplete: (ResponseStatus) -> Unit,
               onError: (Throwable?) -> Unit ) {
        getContatoAPI()
            .excluir(id)
            .enqueue(object : Callback<ResponseStatus>{
                override fun onFailure(call: Call<ResponseStatus>, t: Throwable) {
                    onError(t)
                }
                override fun onResponse(call: Call<ResponseStatus>, response: Response<ResponseStatus>) {
                    if (response.isSuccessful){
                        onComplete(response.body()!!)
                    }else{
                        onError(Throwable("Problemas ao Excluir Cantato !"))
                    }
                }
            })
    }

}