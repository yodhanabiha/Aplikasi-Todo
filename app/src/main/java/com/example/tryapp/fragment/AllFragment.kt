package com.example.tryapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.tryapp.R
import com.example.tryapp.activity.AllActivity
import com.example.tryapp.activity.EditActivity
import com.example.tryapp.adapter.TaskAdapter
import com.example.tryapp.adapter.TaskAdapterComplete
import com.example.tryapp.database.DatabaseClient
import com.example.tryapp.database.TaskDAO
import com.example.tryapp.database.TaskModel
import com.example.tryapp.databinding.FragmentAllBinding
import com.example.tryapp.util.dateToLong
import com.example.tryapp.util.dateToday


class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    private lateinit var database: TaskDAO
    private lateinit var taskSelected: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        database = DatabaseClient.getService(requireActivity()).taskDAO()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setupListener()
        setupData()
    }

    override fun onStart() {
        super.onStart()
        setupData()
    }

    private fun setupList(){
        binding.todoTask.adapter = taskAdapter
        binding.todoTaskCompleted.adapter = taskAdapterComplete
    }

    private fun setupListener(){
        binding.buttonMenu.setOnClickListener {
            PopupMenu( requireActivity() , it).apply {
                setOnMenuItemClickListener { item->
                    when(item?.itemId){
                        R.id.action_new -> {
                            findNavController().navigate(R.id.action_allFragment_to_addFragment2)
                            true
                        }
                        R.id.action_delete_all -> {
                            Thread{database.DeleteAll()}.start()
                            true
                        }
                        R.id.action_delete_complete -> {
                            Thread{database.DeleteCompleted()}.start()
                            true
                        }
                        else -> false
                    }

                }
                inflate(R.menu.menu_task_all)
                show()
            }
        }
        binding.labelSelesai.setOnClickListener {
            if(binding.todoTaskCompleted.visibility == View.GONE){
                binding.todoTaskCompleted.visibility = View.VISIBLE
                binding.imageDone.setImageResource(R.drawable.ic_arrow_down)
            }else{
                binding.todoTaskCompleted.visibility = View.GONE
                binding.imageDone.setImageResource(R.drawable.ic_arrow_right)
            }
        }

    }

    private fun setupData(){
        database.taskAll(
            completed = false,
        ).observe(viewLifecycleOwner,{
            Log.e("taskAll",it.toString())
            taskAdapter.addList(it)
            binding.labelAlert.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        })
        database.taskAll(
            completed = true,
        ).observe(viewLifecycleOwner,{
            Log.e("taskAllCompleted",it.toString())
            taskAdapterComplete.addList(it)
            binding.imageDone.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
            binding.labelSelesai.visibility = if(it.isEmpty()) View.GONE else View.VISIBLE
        })
    }

    private val taskAdapter by lazy {
        TaskAdapter(arrayListOf(),object : TaskAdapter.AdapterListener{
            override fun onUpdate(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.completed = true
                Thread{database.update(taskSelected)}.start()

            }
            override fun onDetail(taskModel: TaskModel) {
                startActivity(
                    Intent(requireActivity(), EditActivity::class.java)
                        .putExtra("intent_task",taskModel)
                )
            }
        })
    }

    private val taskAdapterComplete by lazy {
        TaskAdapterComplete(arrayListOf(),object : TaskAdapterComplete.AdapterListener{
            override fun onClick(taskModel: TaskModel) {
                taskSelected = taskModel
                taskSelected.completed = false
                Thread{database.update(taskSelected)}.start()
            }
        })
    }


}