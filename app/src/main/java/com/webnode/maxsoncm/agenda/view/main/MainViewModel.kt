package com.webnode.maxsoncm.agenda.view.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.webnode.maxsoncm.agenda.model.Contato
import com.webnode.maxsoncm.agenda.repository.ContatoRepository

class MainViewModel : ViewModel() {
    val notaRepository = ContatoRepository()

    val agenda : MutableLiveData<List<Contato>> = MutableLiveData()
    val mensagemErro : MutableLiveData<String> = MutableLiveData()
    val isLoading : MutableLiveData<Boolean> = MutableLiveData()

    fun buscarTodos(){
        isLoading.value = true
        notaRepository.buscar(
            onComplete = {
                isLoading.value = false
                agenda.value = it
            },
            onError = {
                isLoading.value = false
                mensagemErro.value = it?.message
            }
        )
    }
    fun buscarTodos(filtro: String){
        isLoading.value = true
        notaRepository.buscar(filtro,
            onComplete = {
                isLoading.value = false
                agenda.value = it
            },
            onError = {
                isLoading.value = false
                mensagemErro.value = it?.message
            }
        )
    }
}