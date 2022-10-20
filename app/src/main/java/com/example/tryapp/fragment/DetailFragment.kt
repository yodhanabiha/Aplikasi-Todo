package com.example.tryapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.tryapp.R
import com.example.tryapp.database.TaskModel
import com.example.tryapp.databinding.FragmentDetailBinding
import com.example.tryapp.util.dateTostring
import com.example.tryapp.util.timeTostring

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var detail: TaskModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        detail = requireActivity().intent.getSerializableExtra("intent_task") as TaskModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.course.text = detail.task
        binding.courseAddDate.text = dateTostring(detail.date)
        binding.courseAddTime.text = timeTostring(detail.time)

        binding.imgEdit.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_updateFragment,
            bundleOf("argument_task" to detail))
        }
    }
}

