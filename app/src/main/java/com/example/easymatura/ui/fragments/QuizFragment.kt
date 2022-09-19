package com.example.easymatura.ui.fragments

import android.app.AlertDialog
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easymatura.R
import com.example.easymatura.databinding.FragmentQuizBinding
import com.example.easymatura.models.Quiz
import com.example.easymatura.viewmodels.QuizViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.HashMap


class QuizFragment : Fragment() {
    private lateinit var binding: FragmentQuizBinding
    private val viewModel : QuizViewModel by viewModel()
    private val args: QuizFragmentArgs by navArgs()
    private var canAnswer: Boolean = false
    private var suma : Int = 0
    private var incorrect : Int = 0
    private var unanswered : Int = 0

    private var optionA : Int = 0
    private var optionB : Int = 0
    private var optionC : Int = 0
    private var optionD : Int = 0

    private lateinit var mSoundPool: SoundPool
    private var mLoaded: Boolean = false
    var mSoundMap: HashMap<Int, Int> = HashMap()

    private var milis: Long = 60
    private lateinit var timer : CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        this.loadSounds()
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("milis", milis)
        outState.putInt("suma", suma)
        outState.putInt("incorrect", incorrect)
        outState.putInt("unanswered", unanswered)
        outState.putInt("optionA", optionA)
        outState.putInt("optionB", optionB)
        outState.putInt("optionC", optionC)
        outState.putInt("optionD", optionD)
        outState.putBoolean("canAnswer", canAnswer)
    }

    private fun loadSounds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mSoundPool = SoundPool.Builder().setMaxStreams(10).build()
        } else {
            this.mSoundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }
        this.mSoundPool.setOnLoadCompleteListener { _, _, _ -> mLoaded = true }
        this.mSoundMap[R.raw.correct_answer] = this.mSoundPool.load(context, R.raw.correct_answer, 1)
        this.mSoundMap[R.raw.incorrect_answer] = this.mSoundPool.load(context, R.raw.incorrect_answer, 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callback()

        viewModel.getQuizData(args.level, args.exam)
        suma = args.suma
        incorrect = args.incorrect
        unanswered = args.unanswered
        if(savedInstanceState != null) {

            suma = savedInstanceState.getInt("suma")
            incorrect = savedInstanceState.getInt("incorrect")
            unanswered = savedInstanceState.getInt("unanswered")
            milis = savedInstanceState.getLong("milis")
            optionA = savedInstanceState.getInt("optionA")
            optionB = savedInstanceState.getInt("optionB")
            optionC = savedInstanceState.getInt("optionC")
            optionD = savedInstanceState.getInt("optionD")
            canAnswer = savedInstanceState.getBoolean("canAnswer")
        }

        viewModel.quizes.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                var quiz = list.get(args.index)
                binding.tvQuestionNumber.text = (args.index + 1).toString()
                binding.tvQuestionText.text = quiz.question.toString()
                binding.btnOptionA.text = quiz.option_a.toString()
                binding.btnOptionB.text = quiz.option_b.toString()
                binding.btnOptionC.text = quiz.option_c.toString()
                binding.btnOptionD.text = quiz.option_d.toString()

                if(optionA == 0 && optionB == 0 && optionC == 0 && optionD == 0){
                    if(milis > 0){
                        canAnswer = true
                        startTimer(milis)
                    }
                }

                if(optionA == 1) {
                    binding.btnOptionA.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                    binding.tvTime.text = milis.toString()
                } else if(optionA == -1){
                    binding.btnOptionA.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                    binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                    binding.tvTime.text = milis.toString()
                }

                if(optionB == 1) {
                    binding.btnOptionB.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                    binding.tvTime.text = milis.toString()
                } else if(optionB == -1){
                    binding.btnOptionB.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                    binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                    binding.tvTime.text = milis.toString()
                }
                if(optionC == 1) {
                    binding.btnOptionC.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                    binding.tvTime.text = milis.toString()
                } else if(optionC == -1){
                    binding.btnOptionC.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                    binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                    binding.tvTime.text = milis.toString()
                }

                if(optionD == 1) {
                    binding.btnOptionD.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                    binding.tvTime.text = milis.toString()
                } else if(optionD == -1){
                    binding.btnOptionD.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                    binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                    binding.tvTime.text = milis.toString()
                }

                    binding.btnOptionA.setOnClickListener { verifyAnswer(quiz, binding.btnOptionA) }
                    binding.btnOptionB.setOnClickListener { verifyAnswer(quiz, binding.btnOptionB) }
                    binding.btnOptionC.setOnClickListener { verifyAnswer(quiz, binding.btnOptionC) }
                    binding.btnOptionD.setOnClickListener { verifyAnswer(quiz, binding.btnOptionD) }


                var i = args.index + 1
                if (i == list.size){
                    binding.btnNext.text = "ZAVRŠI TEST"
                }
                binding.btnNext.setOnClickListener{
                    if((suma == args.suma) && (incorrect == args.incorrect)) {
                        unanswered++
                    }

                    if (i == list.size){
                        val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(suma, incorrect, unanswered, list.size)
                        findNavController().navigate(action)

                    }else {
                        val action = QuizFragmentDirections.actionQuizFragmentSelf(
                            args.exam,
                            args.level,
                            i, suma, incorrect, unanswered
                        )
                        findNavController().navigate(action)
                    }
                    mSoundPool.release()
                }
            }
        }
    }

    private fun startTimer(milis: Long) {
        timer = object : CountDownTimer(milis * 1000,1000){

            override fun onTick(millisUntilFinished: Long) {
                this@QuizFragment.milis = millisUntilFinished/1000
                binding.tvTime.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                canAnswer = false
            }
        }
        timer.start()
    }

    private fun callback() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Izlazak iz testa")
                .setMessage("Jeste li sigurni da želite izaći iz testa?")
                .setPositiveButton(
                    "DA"
                ) { dialog, which ->
                    findNavController().navigate(R.id.levelFragment)
                }
                .setNegativeButton("NE", null)
                .show()
        }
    }

    private fun verifyAnswer(quiz: Quiz, btn: View) {
        var sumOfCorrectAnswers  = 0
        var sumOfWrongAnswers = 0
        if(canAnswer){
            when(btn.id){
                binding.btnOptionA.id ->
                    if(binding.btnOptionA.text.toString() == quiz.answer.toString()){
                        sumOfCorrectAnswers++
                        binding.btnOptionA.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                        playSound(R.raw.correct_answer)
                        optionA = 1
                    }else{
                        sumOfWrongAnswers++
                        binding.btnOptionA.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                        binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                        playSound(R.raw.incorrect_answer)
                        optionA = -1
                    }

                binding.btnOptionB.id ->
                    if(binding.btnOptionB.text.toString() == quiz.answer.toString()){
                        sumOfCorrectAnswers++
                        binding.btnOptionB.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                        playSound(R.raw.correct_answer)
                        optionB = 1
                    }else{
                        sumOfWrongAnswers++
                        binding.btnOptionB.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                        binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                        playSound(R.raw.incorrect_answer)
                        optionB = -1
                    }

                binding.btnOptionC.id ->
                    if(binding.btnOptionC.text.toString() == quiz.answer.toString()){
                        sumOfCorrectAnswers++
                        binding.btnOptionC.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                        playSound(R.raw.correct_answer)
                        optionC = 1
                    }else{
                        sumOfWrongAnswers++
                        binding.btnOptionC.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                        binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                        playSound(R.raw.incorrect_answer)
                        optionC = -1
                    }

                binding.btnOptionD.id ->
                    if(binding.btnOptionD.text.toString() == quiz.answer.toString()){
                        sumOfCorrectAnswers++
                        binding.btnOptionD.backgroundTintList = resources.getColorStateList(R.color.my_green, null)
                        playSound(R.raw.correct_answer)
                        optionD = 1
                    }else{
                        sumOfWrongAnswers++
                        binding.btnOptionD.backgroundTintList = resources.getColorStateList(R.color.my_red, null)
                        binding.tvVerifyAnswer.text = "Točan odgovor je: " + quiz.answer.toString()
                        playSound(R.raw.incorrect_answer)
                        optionD = -1
                    }
            }
            suma += sumOfCorrectAnswers
            incorrect+= sumOfWrongAnswers
            timer.cancel()
        }
        canAnswer = false
    }

    private fun playSound(selectedAnswer: Int) {
        val soundID = this.mSoundMap[selectedAnswer] ?: 0
        this.mSoundPool.play(soundID, 1f, 1f, 1, 0, 1f)
    }
}