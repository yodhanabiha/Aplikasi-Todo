package com.example.tryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tryapp.database.TaskModel
import com.example.tryapp.databinding.AdapterTaskBinding
import com.example.tryapp.util.dateTostring
import com.example.tryapp.util.timeTostring

class TaskAdapter(
    var items: ArrayList<TaskModel>,
    var listener: AdapterListener,
):RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(val binding: AdapterTaskBinding): RecyclerView.ViewHolder( binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        AdapterTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textItem.text= item.task
        holder.binding.dateItem.text= dateTostring(item.date)
        holder.binding.clockItem.text= timeTostring(item.time)
        holder.itemView.setOnClickListener{
            listener.onDetail(item)
        }
        holder.binding.bt0.setOnClickListener {
            listener.onUpdate(item)
        }
    }
    override fun getItemCount() = items.size

    fun  addList(list: List<TaskModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
    interface AdapterListener{
        fun onUpdate(taskModel: TaskModel)
        fun onDetail(taskModel: TaskModel)
    }
}