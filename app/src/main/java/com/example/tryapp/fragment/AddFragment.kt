package com.example.tryapp.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.tryapp.database.DatabaseClient
import com.example.tryapp.database.TaskDAO
import com.example.tryapp.database.TaskModel
import com.example.tryapp.databinding.FragmentAddBinding
import com.example.tryapp.util.*


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var database: TaskDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater,container,false)
        database = DatabaseClient.getService(requireActivity()).taskDAO()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.courseDate.text = dateToday()
        binding.courseTime.text = TimeNow()

        binding.courseAddDate.setOnClickListener {
            val datepicker = DatePickerDialog.OnDateSetListener{view,year,month,dayofmonth ->
                binding.courseDate.text = dateTostring(year,month,dayofmonth)
            }
            dateToDialog(requireActivity(),datepicker).show()
        }

        binding.courseAddTime.setOnClickListener {
            val timepicker = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                binding.courseTime.text = timeTostring(hour,minute)
            }
            timetoDialog(requireActivity(),timepicker).show()
        }

        binding.courseButton.setOnClickListener{
            val task = TaskModel(0,
                binding.courseText.text.toString(),
                false,
                dateToLong(binding.courseDate.text.toString()),
                timeToLong(binding.courseTime.text.toString()),
            )
            Thread{
                database.insert(task)
                requireActivity().runOnUiThread{
                    Toast.makeText(requireActivity(), "Task Saved", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }.start()
        }
    }



}