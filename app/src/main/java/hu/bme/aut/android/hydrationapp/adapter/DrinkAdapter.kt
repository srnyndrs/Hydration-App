package hu.bme.aut.android.hydrationapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.hydrationapp.R
import hu.bme.aut.android.hydrationapp.databinding.RowDrinkBinding
import hu.bme.aut.android.hydrationapp.model.Drink

class DrinkAdapter : ListAdapter<Drink, DrinkAdapter.ViewHolder>(itemCallback) {

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Drink>(){
            override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
                return oldItem == newItem
            }
        }
    }

    var itemClickListener: DrinkItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowDrinkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drink = this.getItem(position)
        holder.drink = drink
        holder.binding.tvDrinkType.text = drink.type.toString()

        val amount = "${drink.amount} ml"
        holder.binding.tvAmount.text = amount
        holder.binding.tvTime.text = drink.time

        val resource = when (drink.type) {
            Drink.DrinkType.WATER -> R.drawable.water
            Drink.DrinkType.TEA -> R.drawable.tea
            Drink.DrinkType.COFFEE-> R.drawable.coffee
            Drink.DrinkType.ALCOHOL -> R.drawable.alcohol
        }
        holder.binding.ivType.setImageResource(resource)
    }

    inner class ViewHolder(val binding: RowDrinkBinding) : RecyclerView.ViewHolder(binding.root) {
        var drink: Drink? = null

        init {
            itemView.setOnLongClickListener { view ->
                drink?.let {drink -> itemClickListener?.onItemLongClick(adapterPosition, view, drink) }
                true
            }
        }
    }

    interface DrinkItemClickListener {
        fun onItemLongClick(position: Int, view: View, drink: Drink): Boolean
    }
}