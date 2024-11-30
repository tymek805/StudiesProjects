package com.tymek805.exercise_04.database

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tymek805.exercise_04.R
import com.tymek805.exercise_04.databinding.ListRowBinding

class MyListAdapter(private val onItemAction: (item: MyItem, action: Int) -> Unit) :
    ListAdapter<MyItem, MyListAdapter.MyViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<MyItem>() {
            override fun areItemsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MyItem, newItem: MyItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MyViewHolder(listRowBinding: ListRowBinding) :
        RecyclerView.ViewHolder(listRowBinding.root) {
        val tv1: TextView = listRowBinding.lrowTv1
        val tv2: TextView = listRowBinding.lrowTv2
        val img: ImageView = listRowBinding.lrowImage
        val checkBox: CheckBox = listRowBinding.lrowCheckBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listRowBinding =
            ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MyViewHolder(listRowBinding)

        holder.itemView.setOnClickListener {
            onItemAction(getItem(holder.adapterPosition), 1)
        }

        holder.itemView.setOnLongClickListener {
            onItemAction(getItem(holder.adapterPosition), 2)
            // submitList(newdatalist)
            true
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: MyItem = getItem(position)

        holder.tv1.text = item.textMain
        holder.tv2.text = buildString {
            append(item.subText)
            append(item.rating)
        }
        holder.checkBox.isChecked = item.checked
        when (item.itemType) {
            false -> holder.img.setImageResource(R.drawable.ic_airplane)
            true -> holder.img.setImageResource(R.drawable.ic_train)
        }
    }
}