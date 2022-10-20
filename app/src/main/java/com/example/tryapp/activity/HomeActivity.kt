package com.example.tryapp.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.tryapp.R
import com.example.tryapp.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}