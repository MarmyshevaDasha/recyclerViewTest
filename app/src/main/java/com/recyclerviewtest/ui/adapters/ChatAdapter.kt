package com.recyclerviewtest.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recyclerviewtest.R

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.SingleViewHolder>() {
    var items: MutableList<Int> = mutableListOf()
    init {
        for (i in 0..99){
            items.add(i)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_chat_single, parent, false)
        Log.d("M.ChatAdapter", "onCreateViewHolder")
        return SingleViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        Log.d("M.ChatAdapter", "onCreateViewHolder $position")
        holder.bind(items[position])
    }

    fun updateData(data: MutableList<Int>) {
        items = data
        notifyDataSetChanged()
    }

    inner class SingleViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) {
        private val tvTitle = convertView.findViewById<TextView>(R.id.tv_title_single)

        fun bind(item: Int) {
            tvTitle.text = item.toString()
        }
    }
}
