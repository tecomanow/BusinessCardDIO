package br.com.mateusr.businesscarddio.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.mateusr.businesscarddio.R
import br.com.mateusr.businesscarddio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inicializeListener()
    }

    private fun inicializeListener(){
        binding.fabAddCard.setOnClickListener {
            val i = Intent(this, AddBusinessCardActivity::class.java)
            startActivity(i)
        }
    }
}