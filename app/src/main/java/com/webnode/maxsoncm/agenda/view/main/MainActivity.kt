package com.webnode.maxsoncm.agenda.view.main

import android.app.Activity
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.webnode.maxsoncm.agenda.R
import com.webnode.maxsoncm.agenda.model.Contato
import com.webnode.maxsoncm.agenda.view.formulario.FormularioActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.loading.*
import kotlinx.android.synthetic.main.contato_item.*


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainViewModel = ViewModelProviders.of( this)
            .get(MainViewModel:: class.java)

        registerObservers()

        if (Intent.ACTION_SEARCH != intent.action) {
            mainViewModel.buscarTodos()
        }
        fab.setOnClickListener {
            startActivityForResult( Intent(this,
                FormularioActivity::class.java ), 1 )
        }

        handleIntent(intent)

        rvContatos.setOnClickListener {

            val nextScreenIntent = Intent(this, FormularioActivity::class.java)

            nextScreenIntent.putExtra("ID", tvId.text)
            nextScreenIntent.putExtra("NOME", tvNome.text)
            nextScreenIntent.putExtra("EMAIL", tvEmail.text)
            nextScreenIntent.putExtra("TELEFONE", tvTelefone.text)

            startActivityForResult( nextScreenIntent , 1)
        }

        // Set an on refresh listener for swipe refresh layout
        swiperefresh.setOnRefreshListener{
            mainViewModel.buscarTodos()
            swiperefresh.isRefreshing = false
        }

    }
    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }
    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            mainViewModel.buscarTodos(query)
        }
    }

    //rastreia o resultado da tela aberta
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            mainViewModel.buscarTodos()//faz o reload dos resultados
        }
    }

    private fun registerObservers() {
        mainViewModel.isLoading.observe(this, isLoadingObserver)
        mainViewModel.mensagemErro.observe(this, MensagemErro)
        mainViewModel.agenda.observe(this, agendaObserver)
    }

    private  var agendaObserver = Observer<List<Contato>> {
        rvContatos.adapter = MainListAdapter(this, it!!) {

            val nextScreenIntent = Intent(this, FormularioActivity::class.java)
            nextScreenIntent.putExtra("ID", it.age_id)
            nextScreenIntent.putExtra("NOME", it.age_nome)
            nextScreenIntent.putExtra("EMAIL", it.age_email)
            nextScreenIntent.putExtra("TELEFONE", it.age_telefone)

            startActivityForResult(nextScreenIntent, 1)
        }
        rvContatos.layoutManager = LinearLayoutManager(this)

    }

    private var MensagemErro = Observer<String> {
        if(it!!.isNotEmpty()) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private var isLoadingObserver = Observer<Boolean> {
        if(it == true) {
            containerLoading.visibility = View.VISIBLE
        } else {
            containerLoading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
