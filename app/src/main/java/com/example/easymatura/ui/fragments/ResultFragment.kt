package com.example.easymatura.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easymatura.R
import com.example.easymatura.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val args: ResultFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(layoutInflater)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.levelFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCorrect.text = args.correctAnswers.toString()
        binding.tvIncorrect.text = args.incorrectAnswers.toString()
        binding.tvNotAnswered.text = args.unanswered.toString()
        binding.tvPercentage.text = countPercentage()

        binding.btnHome.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToLevelFragment()
            findNavController().navigate(action)
        }
    }

    private fun countPercentage(): CharSequence? {
        var result = ((args.correctAnswers.toDouble() / args.listSize) * 100).toInt()
        var percentage = result.toString() + "%"
        return percentage
    }
}