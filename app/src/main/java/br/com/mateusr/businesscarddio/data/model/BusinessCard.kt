package br.com.mateusr.businesscarddio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusinessCard(
    @PrimaryKey (autoGenerate = true)
    val id : Int,
    @ColumnInfo (name = "nome")
    val nome : String,
    @ColumnInfo (name = "telefone")
    val telefone : String,
    @ColumnInfo (name = "empresa")
    val empresa : String,
    @ColumnInfo (name = "email")
    val email : String,
    @ColumnInfo (name = "cor")
    val cor : Int,
    @ColumnInfo (name = "textColor")
    val textColor : Int
)