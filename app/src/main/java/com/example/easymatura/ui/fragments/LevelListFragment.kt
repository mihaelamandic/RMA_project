package com.example.easymatura.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easymatura.databinding.FragmentLevelListBinding
import com.example.easymatura.models.Level
import com.example.easymatura.ui.adapters.LevelAdapter
import com.example.easymatura.ui.adapters.OnLevelSelectedListener
import com.example.easymatura.viewmodels.LevelViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LevelListFragment : Fragment(), OnLevelSelectedListener{
    private lateinit var binding: FragmentLevelListBinding
    private lateinit var adapter: LevelAdapter
    private val viewModel : LevelViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLevelListBinding.inflate(layoutInflater)
        viewModel.getLevels()
        setupRecyclerView()
        viewModel.levels.observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
                adapter.setLevels(it)
            }
        }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity!!.finish()
        }
        return binding.root

    }

    private fun setupRecyclerView() {
        binding.levelList.layoutManager= LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = LevelAdapter()
        adapter.onLevelSelectedListener = this
        binding.levelList.adapter = adapter
    }


    override fun onLevelSelected(level: Level) {
        val action = LevelListFragmentDirections.actionLevelFragmentToExamListFragment(level)
        findNavController().navigate(action)
    }
}