package br.com.mateusr.businesscarddio.data

import br.com.mateusr.businesscarddio.data.daos.BusinessCardDao
import br.com.mateusr.businesscarddio.data.model.BusinessCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BusinessCardRepository(private val dao : BusinessCardDao) {

    fun insert(businessCard: BusinessCard) = runBlocking {
        launch(Dispatchers.IO){
            dao.insert(businessCard)
        }
    }

    fun delete(businessCard: BusinessCard) = runBlocking {
        launch(Dispatchers.IO){
            dao.delete(businessCard)
        }
    }

    fun update(businessCard: BusinessCard) = runBlocking {
        launch(Dispatchers.IO){
            dao.update(businessCard)
        }
    }

    fun getAllBusinessCards() = dao.getAllCards()

    fun getCardsById(id : Int) = dao.getCardById(id)

}