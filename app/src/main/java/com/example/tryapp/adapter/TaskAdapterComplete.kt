package com.example.tryapp.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryapp.database.TaskModel
import com.example.tryapp.databinding.AdapterTaskCompleteBinding
import com.example.tryapp.util.dateTostring
import com.example.tryapp.util.timeTostring

class TaskAdapterComplete(
    var items: ArrayList<TaskModel>,
    var listener: AdapterListener,
):RecyclerView.Adapter<TaskAdapterComplete.ViewHolder>() {
    class ViewHolder(val binding: AdapterTaskCompleteBinding): RecyclerView.ViewHolder( binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterTaskCompleteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textItem.text= item.task
        holder.binding.textItem.paintFlags= holder.binding.textItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        holder.binding.dateItem.text= dateTostring(item.date)
        holder.binding.clockItem.text= timeTostring(item.time)
        holder.binding.bt0.setOnClickListener{
            listener.onClick(item)
        }
    }
    override fun getItemCount() = items.size
    fun  addList(list: List<TaskModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    interface AdapterListener{
        fun onClick(taskModel: TaskModel)
    }
}