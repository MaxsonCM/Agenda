package com.webnode.maxsoncm.agenda.view.formulario


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.webnode.maxsoncm.agenda.extensions.isNumeric
import com.webnode.maxsoncm.agenda.model.Contato
import com.webnode.maxsoncm.agenda.model.ResponseStatus
import com.webnode.maxsoncm.agenda.repository.ContatoRepository


class FormularioViewModel : ViewModel() {

    val notaRepository = ContatoRepository()
    val responseStatus: MutableLiveData<ResponseStatus> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun salvar(age_id: String, age_nome: String, age_email: String, age_telefone: String) {
        isLoading.value = true
        var id = "0"

        if ( isNumeric(age_id) ) { id = age_id }

        val contato = Contato(age_id = id, age_nome = age_nome, age_email = age_email, age_telefone = age_telefone)

        notaRepository.salvar(contato,
            onComplete = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    true,
                    "Dado gravado com sucesso"
                )
            }, onError = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    false,
                    it?.message!!
                )
            }
        )

    }

    fun excluir(age_id: String) {
        isLoading.value = true

        notaRepository.excluir (age_id,
            onComplete = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    true,
                    "Dado excluido com sucesso"
                )
            }, onError = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    false,
                    it?.message!!
                )
            }
        )

    }

}