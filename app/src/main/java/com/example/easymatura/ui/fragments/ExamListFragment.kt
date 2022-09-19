package com.example.easymatura.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easymatura.databinding.FragmentExamListBinding
import com.example.easymatura.models.Exam
import com.example.easymatura.ui.adapters.ExamListAdapter
import com.example.easymatura.ui.adapters.OnExamSelectedListener
import com.example.easymatura.viewmodels.ExamListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExamListFragment : Fragment(), OnExamSelectedListener {
    private lateinit var binding: FragmentExamListBinding
    private lateinit var adapter: ExamListAdapter
    private val viewModel : ExamListViewModel by viewModel()
    private val args: ExamListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExamListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getExams(args.level)

        setupRecyclerView()
        viewModel.exams.observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) {
                adapter.setExams(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.examList.layoutManager= LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = ExamListAdapter()
        adapter.onExamSelectedListener = this
        binding.examList.adapter = adapter
    }

    override fun onExamSelected(exam: Exam) {
        val action = ExamListFragmentDirections.actionExamListFragmentToStartExamFragment(exam, args.level)
        findNavController().navigate(action)
    }

}