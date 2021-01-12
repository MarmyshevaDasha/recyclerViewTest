package com.recyclerviewtest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recyclerviewtest.R

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.SingleViewHolder>() {
    private var items: List<Int> = listOf()
    var clickListener: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return SingleViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(data: List<Int>) {
        items = data
        if (items.isEmpty()) notifyDataSetChanged()
    }

    fun deleteItem(item: Int) {
        notifyItemRemoved(item)
    }

    fun insertItem(item: Int) {
        notifyItemInserted(item)
    }

    inner class SingleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle = view.findViewById<TextView>(R.id.tv_title_single)
        private val buttonDelete = view.findViewById<TextView>(R.id.delete)

        fun bind(item: Int) {
            tvTitle.text = item.toString()
            buttonDelete.setOnClickListener {
                clickListener(this.adapterPosition)
            }
        }
    }
}
