package com.example.easymatura.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easymatura.databinding.FragmentLevelBinding
import java.text.SimpleDateFormat

class LevelFragment : Fragment(){
    private lateinit var binding: FragmentLevelBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLevelBinding.inflate(layoutInflater)
        binding.tvVisaRazina.setOnClickListener{saveTask()}
        binding.tvNizaRazina.setOnClickListener{saveTask()}
        return binding.root
    }

    private fun saveTask() {


    }

}