package com.example.assignment1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val items: List<ShoppingItem>,
    private val onItemChecked: (position: Int, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.itemCheckBox)
        val nameTextView: TextView = view.findViewById(R.id.itemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = "${item.name} (Quantity: ${item.quantity})"
        holder.checkBox.isChecked = item.isPurchased

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onItemChecked(position, isChecked)
        }
    }

    override fun getItemCount() = items.size
}