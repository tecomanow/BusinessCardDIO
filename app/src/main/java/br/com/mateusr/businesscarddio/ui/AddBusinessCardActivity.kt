package br.com.mateusr.businesscarddio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.mateusr.businesscarddio.databinding.ActivityAddBusinessCardBinding

class AddBusinessCardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBusinessCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddBusinessCardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializeListener()
    }

    private fun inicializeListener(){
        binding.btnCloseActivity.setOnClickListener {
            finish()
        }

        binding.btnConfirmarCard.setOnClickListener {
            saveBusinessCard()
        }
    }

    private fun saveBusinessCard() {
        TODO("Not yet implemented")
    }
}