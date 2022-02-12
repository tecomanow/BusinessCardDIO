package br.com.mateusr.businesscarddio.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mateusr.businesscarddio.R
import br.com.mateusr.businesscarddio.data.model.BusinessCard
import br.com.mateusr.businesscarddio.databinding.AdapterCardBinding

class AdapterBusinessCard(
) : ListAdapter<BusinessCard, AdapterBusinessCard.MyBusinessCardViewHolder>(DiffCallback()) {

    var listenerDelete : (card : BusinessCard) -> Unit = {}
    var listenerUpdate : (cardId : Int) -> Unit = {}
    var listenerShare : (View) -> Unit = {}

    inner class MyBusinessCardViewHolder(private val binding : AdapterCardBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(card : BusinessCard){

            binding.apply {
                textViewName.text = card.nome
                textViewPhone.text = card.telefone
                textViewEmail.text = card.email
                textViewCompany.text = card.empresa

                constraintBackground.setOnClickListener {
                    showPopUpMenu(card)
                }

                setColors(card)
            }

        }

        private fun showPopUpMenu(card: BusinessCard) {
            val popupMenu = PopupMenu(binding.constraintBackground.context, binding.constraintBackground)
            popupMenu.menuInflater.inflate(R.menu.card_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){

                    R.id.actionMenuShare -> {
                        listenerShare(binding.root)
                    }

                    R.id.actionMenuEdit -> {
                        listenerUpdate(card.id)
                    }

                    R.id.actionMenuDelete -> {
                        listenerDelete(card)
                    }

                }

                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

        private fun setColors(card: BusinessCard) {
            binding.apply {
                textViewName.setTextColor(card.textColor)
                textViewPhone.setTextColor(card.textColor)
                textViewEmail.setTextColor(card.textColor)
                textViewCompany.setTextColor(card.textColor)

                constraintBackground.setBackgroundColor(card.cor)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBusinessCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCardBinding.inflate(inflater, parent, false)
        return MyBusinessCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyBusinessCardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<BusinessCard>(){
        override fun areItemsTheSame(oldItem: BusinessCard, newItem: BusinessCard): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BusinessCard, newItem: BusinessCard): Boolean {
            return oldItem.id == newItem.id
        }

    }

}