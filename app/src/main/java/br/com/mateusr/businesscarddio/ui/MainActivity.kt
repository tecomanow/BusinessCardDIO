package br.com.mateusr.businesscarddio.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mateusr.businesscarddio.data.model.BusinessCard
import br.com.mateusr.businesscarddio.databinding.ActivityMainBinding
import br.com.mateusr.businesscarddio.ui.adapters.AdapterBusinessCard
import br.com.mateusr.businesscarddio.util.Image
import br.com.mateusr.businesscarddio.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val mainViewModel : MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(application)
    }

    private val businessCardAdapter : AdapterBusinessCard by lazy {
        AdapterBusinessCard()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecyclerView()
        inicializeListener()
        setEmptyVisibility(false)
        initObservers()
    }

    private fun initObservers() {
        mainViewModel.getAllBusinessCard().observe(this) {
            checkEmptyList(it)
            populateRecyclerView(it)
        }
    }

    private fun populateRecyclerView(list: List<BusinessCard>) {
        businessCardAdapter.submitList(list)
        businessCardAdapter.notifyDataSetChanged()
    }

    private fun checkEmptyList(list: List<BusinessCard>) {
        if(list.isNotEmpty()){
            setEmptyVisibility(false)
        }else{
            setEmptyVisibility(true)
        }
    }

    private fun configureRecyclerView(){
        binding.recyclerViewCards.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = businessCardAdapter
        }
    }

    private fun inicializeListener(){

        binding.fabAddCard.setOnClickListener {
            val i = Intent(this, AddBusinessCardActivity::class.java)
            startActivity(i)
        }

        businessCardAdapter.listenerDelete = {
            mainViewModel.deleteBusinessCard(it)
        }

        businessCardAdapter.listenerUpdate = {
            val i = Intent(this, AddBusinessCardActivity::class.java)
            i.putExtra("cardId", it)
            startActivity(i)
        }

        businessCardAdapter.listenerShare = {
            Image.share(this@MainActivity, it)
        }

    }

    private fun setEmptyVisibility(visibility : Boolean){
        if (visibility) binding.linearLayoutEmpyt.visibility = View.VISIBLE else binding.linearLayoutEmpyt.visibility = View.GONE
    }
}