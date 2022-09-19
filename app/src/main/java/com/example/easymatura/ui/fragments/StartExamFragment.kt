package com.example.easymatura.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easymatura.databinding.FragmentStartExamBinding


class StartExamFragment : Fragment() {
     private lateinit var binding: FragmentStartExamBinding
    private val args: StartExamFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartExamBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val levelDescription: String = binding.tvDescType.text.toString() + " ${args.levelRef.name}"
        val examDescription : String = binding.tvDescExam.text.toString() + " ${args.exam.name}"
        val time : String = binding.tvDescRule.text.toString()
        val numberOfQuestions  : String = binding.tvDescNmbrOfquestions.text.toString() + " ${args.levelRef.questions}"

        binding.tvDescType.text =  levelDescription
        binding.tvDescExam.text = examDescription
        binding.tvDescRule.text = time
        binding.tvDescNmbrOfquestions.text = numberOfQuestions

        binding.btnStart.setOnClickListener {
            val action = StartExamFragmentDirections.actionStartExamFragmentToQuizFragment(args.exam, args.levelRef, 0, 0, 0, 0)
            findNavController().navigate(action)

        }
    }
}