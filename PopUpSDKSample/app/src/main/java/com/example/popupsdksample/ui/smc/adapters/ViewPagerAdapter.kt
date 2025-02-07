package com.example.popupsdksample.ui.smc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popupsdksample.R

class ViewPagerAdapter(
    private val onButtonClick: (Int) -> Unit,
    private val onItemClick: (buttonIndex: Int, itemIndex: Int) -> Unit,
    private val onSendButtonClick: (buttonIndex: Int) -> Unit

) : RecyclerView.Adapter<ViewPagerAdapter.ButtonViewHolder>() {

    private val buttonLabels = listOf(
        "getCustomerChannels",
        "updateCustomerChannels",
        "getCustomerSubscriptions",
        "updateCustomerSubscriptions",
        "addCustomerContacts"
    )

    private val dataMap = mutableMapOf<Int, MutableList<Pair<String, String>>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_button, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        holder.bind(buttonLabels[position], position)
    }

    override fun getItemCount(): Int = buttonLabels.size

    fun updateData(buttonIndex: Int, newData: List<Pair<String?, String>>) {
        val fixedData = newData.map { (first, second) -> Pair(first ?: "", second) }
        dataMap[buttonIndex] = fixedData.toMutableList()
        notifyItemChanged(buttonIndex)
    }

    inner class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.buttonItem)
        private val buttonSend: Button = itemView.findViewById(R.id.button)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.dataRecyclerView)
        private val dataAdapter = DataAdapter { itemIndex: Int -> // Указываем тип
            onItemClick(adapterPosition, itemIndex)
        }

        init {
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)
            recyclerView.adapter = dataAdapter
        }

        fun bind(label: String, position: Int) {
            button.text = label
            button.setOnClickListener { onButtonClick(position) }
            dataMap[position]?.let { dataAdapter.updateData(it) }
            when(position) {
                1,3 -> {
                    buttonSend.visibility = View.VISIBLE
                    buttonSend.setOnClickListener { onSendButtonClick(position) }
                }
                else -> {
                    buttonSend.visibility = View.GONE
                }
            }
            if(position != 1) {

            } else {

            }
        }
    }
}