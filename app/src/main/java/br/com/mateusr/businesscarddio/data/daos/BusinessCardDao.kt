package br.com.mateusr.businesscarddio.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.mateusr.businesscarddio.data.model.BusinessCard

@Dao
interface BusinessCardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(businessCard: BusinessCard)

    @Delete
    fun delete(businessCard: BusinessCard)

    @Update
    fun update(businessCard: BusinessCard)

    @Query("SELECT * FROM BusinessCard")
    fun getAllCards() : LiveData<List<BusinessCard>>

    @Query("SELECT * FROM BusinessCard WHERE id = :id")
    fun getCardById(id : Int) : LiveData<BusinessCard?>

}