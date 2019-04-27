package com.webnode.maxsoncm.agenda.api

import com.webnode.maxsoncm.agenda.model.Contato
import com.webnode.maxsoncm.agenda.model.ResponseStatus
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET

interface ContatoAPI{

    @GET("/agenda/api/contato/")
    fun getAgenda(): Call<List<Contato>>

    @GET("/agenda/api/contato/{filtro}")
    fun getAgendaFiltro(@Path( "filtro") filtro : String ): Call<List<Contato>>

    @POST("/agenda/api/contato/")
    fun salvar(@Body contato: Contato) : Call<Contato>

    @DELETE("/agenda/api/{id}")
    fun excluir(@Path("id") id: String) : Call<ResponseStatus>

}
