package com.example.tryapp.fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Switch
import android.widget.Toast
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import com.example.tryapp.R
import com.example.tryapp.activity.AllActivity
import com.example.tryapp.activity.EditActivity
import com.example.tryapp.adapter.TaskAdapter
import com.example.tryapp.adapter.TaskAdapterComplete
import com.example.tryapp.database.DatabaseClient
import com.example.tryapp.database.TaskDAO
import com.example.tryapp.database.TaskModel
import com.example.tryapp.databinding.FragmentHomeBinding
import com.example.tryapp.util.TimeNow
import com.example.tryapp.util.dateToLong
import com.example.tryapp.util.dateToday
import com.example.tryapp.util.timeToLong
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: TaskDAO
    private lateinit var taskSelected: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        database = DatabaseClient.getService(requireActivity()).taskDAO()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textDate.text = dateToday()
        val cal : Date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("EEEE,   ")
        val formatdate = formatter.format(cal)
        binding.textDay.text = formatdate
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
        binding.labelSelesai.setOnClickListener {
            if(binding.todoTaskCompleted.visibility == View.GONE){
                binding.todoTaskCompleted.visibility = View.VISIBLE
                binding.imageDone.setImageResource(R.drawable.ic_arrow_down)
            }else{
                binding.todoTaskCompleted.visibility = View.GONE
                binding.imageDone.setImageResource(R.drawable.ic_arrow_right)
            }
        }
        binding.buttonTask.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            //testInsert()
        }
        binding.allTask.setOnClickListener {
            startActivity(Intent(requireActivity(), AllActivity::class.java))
        }
    }
    private fun setupData(){
        database.taskAll(
            completed = false,
            date = dateToLong(dateToday()),
        ).observe(viewLifecycleOwner,{
            Log.e("taskAll",it.toString())
            taskAdapter.addList(it)
            binding.labelAlert.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
        })
        database.taskAll(
            completed = true,
            date = dateToLong(dateToday()),
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
                    Intent(requireActivity(),EditActivity::class.java)
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