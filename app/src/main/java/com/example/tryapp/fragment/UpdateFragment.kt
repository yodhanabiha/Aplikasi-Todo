package com.example.tryapp.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tryapp.R
import com.example.tryapp.database.DatabaseClient
import com.example.tryapp.database.TaskDAO
import com.example.tryapp.database.TaskModel
import com.example.tryapp.databinding.FragmentUpdateBinding
import com.example.tryapp.util.*


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var database: TaskDAO
    private lateinit var detail: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater,container, false)
        database = DatabaseClient.getService(requireActivity()).taskDAO()
        detail = requireArguments().getSerializable("argument_task") as TaskModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupListener()
    }

    private fun setupListener() {
        binding.courseText.setText(detail.task)
        binding.courseDate.text = dateTostring(detail.date)
        binding.courseTime.text = timeTostring(detail.time)
    }

    private fun setupData() {

        binding.courseAddDate.setOnClickListener {
            val datepicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayofmonth ->
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

        binding.deleteButton.setOnClickListener {
            Thread{
                database.delete(detail)
                requireActivity().runOnUiThread{
                    Toast.makeText(requireActivity(),"delete complete", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
            }.start()
        }
        binding.saveButton.setOnClickListener {

            detail.task = binding.courseText.text.toString()
            detail.date = dateToLong(binding.courseDate.text.toString())
            detail.time = timeToLong(binding.courseTime.text.toString())

            Thread{
                database.update(detail)
                requireActivity().runOnUiThread{
                    Toast.makeText(requireActivity(),"update saved", Toast.LENGTH_SHORT).show()
                    requireActivity().finish()
                }
            }.start()
        }
    }


}