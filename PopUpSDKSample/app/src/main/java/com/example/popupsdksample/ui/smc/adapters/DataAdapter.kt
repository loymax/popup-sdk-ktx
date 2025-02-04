package com.example.popupsdksample.ui.smc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popupsdksample.R

class DataAdapter(
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    private val dataList = mutableListOf<Pair<String, String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val (type, value) = dataList[position]
        holder.bind(type, value)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int = dataList.size

    fun updateData(newData: List<Pair<String, String>>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeTextView: TextView = itemView.findViewById(R.id.dataType)
        private val valueTextView: TextView = itemView.findViewById(R.id.dataValue)

        fun bind(type: String, value: String) {
            typeTextView.text = "Type: $type"
            valueTextView.text = "Value: $value"
        }
    }
}