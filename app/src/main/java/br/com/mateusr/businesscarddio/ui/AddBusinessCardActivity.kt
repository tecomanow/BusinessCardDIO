package br.com.mateusr.businesscarddio.ui

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import br.com.mateusr.businesscarddio.data.model.BusinessCard
import br.com.mateusr.businesscarddio.databinding.ActivityAddBusinessCardBinding
import br.com.mateusr.businesscarddio.ui.adapters.AdapterBusinessCard
import br.com.mateusr.businesscarddio.util.PhoneNumberFormatType
import br.com.mateusr.businesscarddio.util.PhoneNumberFormatter
import br.com.mateusr.businesscarddio.viewmodels.MainViewModel
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import java.lang.ref.WeakReference

class AddBusinessCardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddBusinessCardBinding
    private var selectedColor = Color.WHITE
    private var selectedTextColor = Color.BLACK

    private val colors = arrayOf("#ffffff", "#000000", "#757575", "#FAFAFA","#F5F5F5","#424242","#CFD8DC","#BA68C8", "#9575CD", "#7986CB", "#64B5F6", "#4FC3F7",
        "#F06292", "#4DD0E1","#4DB6AC","#81C784","#DCE775", "#FFD54F", "#E0E0E0", "#90A4AE", "#546E7A")
    private val mainViewModel : MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(application)
    }
    private var cardId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddBusinessCardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cardId = intent.getIntExtra("cardId", 0)
        if(cardId > 0) {
            supportActionBar?.title = "Editar cartão"
            binding.btnConfirmarCard.text = "Atualizar"
            configureCardObserver(cardId)
        }

        configurePhoneFormatter()
        inicializeListener()
    }

    private fun configurePhoneFormatter() {
        val formatterType = PhoneNumberFormatType.PT_BR
        val formatter = PhoneNumberFormatter(WeakReference(binding.editTextTelefone), formatterType)
        binding.editTextTelefone.addTextChangedListener(formatter)
    }

    private fun configureCardObserver(cardId: Int) {
        mainViewModel.getBusinessCardById(cardId).observe(this){ card ->

            card?.let {
                binding.apply {
                    editTextNome.setText(it.nome)
                    editTextTelefone.setText(it.telefone)
                    editTextEmail.setText(it.email)
                    editTextEmpresa.setText(it.empresa)

                    imageViewCardColor.setBackgroundColor(it.cor)
                    imageViewCardTextColor.setBackgroundColor(it.textColor)

                    selectedColor = it.cor
                    selectedTextColor = it.textColor
                }
            }

        }
    }

    private fun inicializeListener(){
        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnConfirmarCard.setOnClickListener {
            if(cardId > 0){
                updateBusinessCard()
            }else{
                saveBusinessCard()
            }
        }

        binding.imageViewCardColor.setOnClickListener {
            showMaterialColorPicker()
        }

        binding.imageViewCardTextColor.setOnClickListener {
            showMaterialTextColorPicker()
        }
    }

    private fun updateBusinessCard() {
        if(validateInputs()){
            val businessCard = BusinessCard(
                cardId,
                binding.editTextNome.text.toString(),
                binding.editTextTelefone.text.toString(),
                binding.editTextEmpresa.text.toString(),
                binding.editTextEmail.text.toString(),
                selectedColor,
                selectedTextColor
            )

            mainViewModel.updateBusinessCard(businessCard)
            finish()
        }
    }

    private fun showMaterialTextColorPicker() {

        MaterialColorPickerDialog
            .Builder(this)
            .setTitle("Escolha uma cor")
            .setColorShape(ColorShape.SQAURE)
            .setColorSwatch(ColorSwatch._300)
            .setColors(colors)
            //.setDefaultColor("#000000")
            .setColorListener { color, colorHex ->
                binding.imageViewCardTextColor.setBackgroundColor(color)
                selectedTextColor = color
                //Toast.makeText(this, "$color", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showMaterialColorPicker() {
        // Kotlin Code
        /*val colors = arrayOf("#ffffff", "#000000", "#BA68C8", "#9575CD", "#7986CB", "#64B5F6", "#4FC3F7",
            "#F06292", "#4DD0E1","#4DB6AC","#81C784","#DCE775", "#FFD54F", "#E0E0E0", "#90A4AE", "#546E7A")*/

        MaterialColorPickerDialog
            .Builder(this)
            .setTitle("Escolha uma cor")
            .setColorShape(ColorShape.SQAURE)
            .setColorSwatch(ColorSwatch._300)
            .setColors(colors)
            //.setDefaultColor("#ffffff")
            .setColorListener { color, colorHex ->
                binding.imageViewCardColor.setBackgroundColor(color)
                selectedColor = color
            }
            .show()
    }

    private fun saveBusinessCard() {

        if(validateInputs()){
            val businessCard = BusinessCard(
                0,
                binding.editTextNome.text.toString(),
                binding.editTextTelefone.text.toString(),
                binding.editTextEmpresa.text.toString(),
                binding.editTextEmail.text.toString(),
                selectedColor,
                selectedTextColor
            )

            mainViewModel.insertBusinessCard(businessCard)
            finish()

        }
    }

    private fun validateInputs() : Boolean {
        val nome = binding.editTextNome.text.toString()
        val telefone = binding.editTextTelefone.text.toString()
        val empresa = binding.editTextEmpresa.text.toString()
        val email = binding.editTextEmail.text.toString()

        if(nome.isNotBlank()){
            if(telefone.isNotBlank()){
                if(empresa.isNotBlank()){
                    if(email.isNotBlank()){
                        return true
                    }else {
                        binding.editTextEmail.error = "Insira o email"
                        return false
                    }
                }else{
                    binding.editTextEmpresa.error = "Insira o nome da empresa"
                    return false
                }
            }else {
                binding.editTextTelefone.error = "Insira o telefone"
                return false
            }
        }else{
            binding.editTextNome.setError("Insira o nome")
            return false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}