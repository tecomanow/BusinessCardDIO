package br.com.mateusr.businesscarddio.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mateusr.businesscarddio.data.AppDatabase
import br.com.mateusr.businesscarddio.data.BusinessCardRepository
import br.com.mateusr.businesscarddio.data.daos.BusinessCardDao
import br.com.mateusr.businesscarddio.data.model.BusinessCard

class MainViewModel(application: Application) : ViewModel() {

    private val businessCardRepository : BusinessCardRepository

    init {
        val dao = AppDatabase.getDatabase(application).businessCardDao()
        businessCardRepository = BusinessCardRepository(dao)
    }

    fun insertBusinessCard(businessCard: BusinessCard) {
        businessCardRepository.insert(businessCard)
    }

    fun deleteBusinessCard(businessCard: BusinessCard){
        businessCardRepository.delete(businessCard)
    }

    fun updateBusinessCard(businessCard: BusinessCard){
        businessCardRepository.update(businessCard)
    }

    fun getAllBusinessCard() : LiveData<List<BusinessCard>> = businessCardRepository.getAllBusinessCards()

    fun getBusinessCardById(id : Int) : LiveData<BusinessCard?> = businessCardRepository.getCardsById(id)

    class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}