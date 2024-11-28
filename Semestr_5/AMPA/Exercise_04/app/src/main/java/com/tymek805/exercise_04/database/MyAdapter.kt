package com.tymek805.exercise_04.database

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.tymek805.exercise_04.R
import com.tymek805.exercise_04.databinding.ListRowBinding

class MyAdapter(var data: MutableList<MyItem>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    inner class MyViewHolder(listRowBinding : ListRowBinding) : RecyclerView.ViewHolder(listRowBinding.root) {
        val tv1: TextView = listRowBinding.lrowTv1
        val tv2: TextView = listRowBinding.lrowTv2
        val img: ImageView = listRowBinding.lrowImage
        val checkBox: CheckBox = listRowBinding.lrowCheckBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listRowBinding = ListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = MyViewHolder(listRowBinding)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "You clicked: " + (holder.adapterPosition + 1), Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener {
            val position = holder.layoutPosition
            MyRepository.getInstance(holder.itemView.context).deleteItem(data[position])
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, data.size)

            true
        }

        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv1.text = data[position].text_main
        holder.tv2.text = buildString {
            append(data[position].text_2)
            append(data[position].item_value)
        }
        holder.checkBox.isChecked = data[position].item_checked
        when (data[position].item_type) {
            false -> holder.img.setImageResource(R.drawable.ic_airplane)
            true -> holder.img.setImageResource(R.drawable.ic_train)
        }
    }

    override fun getItemCount(): Int = data.size
}