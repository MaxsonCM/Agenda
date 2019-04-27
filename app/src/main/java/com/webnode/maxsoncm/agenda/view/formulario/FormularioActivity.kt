package com.webnode.maxsoncm.agenda.view.formulario

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.webnode.maxsoncm.agenda.R
import com.webnode.maxsoncm.agenda.model.ResponseStatus
import kotlinx.android.synthetic.main.activity_formulario.*
import kotlinx.android.synthetic.main.loading.*


class FormularioActivity : AppCompatActivity() {

    private lateinit var formularioViewModel: FormularioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        inputNome.editText?.setText(intent.getStringExtra("NOME"))
        inputEmail.editText?.setText(intent.getStringExtra("EMAIL"))
        inputTelefone.editText?.setText(intent.getStringExtra("TELEFONE"))

        formularioViewModel = ViewModelProviders.of(this)
            .get(FormularioViewModel::class.java)

        if (intent.getStringExtra("ID") == null) {
            btExcluir.visibility = View.GONE
        }else{
            btExcluir.visibility = View.VISIBLE
        }

        btSalvar.setOnClickListener {
            var id = "0"

            if (intent.getStringExtra("ID") == null) {

            }else{
                id = intent.getStringExtra("ID")
            }

            formularioViewModel.salvar(
                id,
                inputNome.editText?.text.toString(),
                inputEmail.editText?.text.toString(),
                inputTelefone.editText?.text.toString()
            )

        }

        btExcluir.setOnClickListener{
            formularioViewModel.excluir(intent.getStringExtra("ID"))
            //setResult(Activity.RESULT_OK)
            //finish()
        }



        /*inputTelefone.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })*/

        registerObserver()
    }

    private fun registerObserver() {
        formularioViewModel.responseStatus.observe(this, responseObserver)
        formularioViewModel.isLoading.observe(this, loadingObserver)
    }

    private var loadingObserver = Observer<Boolean> {
        if (it == true) {
            containerLoading.visibility = View.VISIBLE
        } else {
            containerLoading.visibility = View.GONE
        }
    }

    private var responseObserver = Observer<ResponseStatus> {
        Toast.makeText(this, it?.mensagem, Toast.LENGTH_SHORT).show()
        if (it?.sucesso == true) {
            setResult(Activity.RESULT_OK)
            finish()
        }

    }



    /*private fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("@string/dialogTitleDelete")

        // Set a message for alert dialog
        builder.setMessage("@string/dialogMessageDelete")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> toast("Positive/Yes button clicked.")
                DialogInterface.BUTTON_NEGATIVE -> toast("Negative/No button clicked.")
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("@string/btYes",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("@string/btNo",dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }*/



}
