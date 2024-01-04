package com.example.myapp2

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapp2.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityMainBinding

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var textQuestionView: TextView
    private lateinit var textResult: TextView
    private lateinit var answerNumber1: TextView
    private lateinit var answerNumber2: TextView
    private lateinit var answerNumber3: TextView
    private lateinit var answerNumber4: TextView
    private lateinit var answerNumber5: TextView
    private lateinit var answerNumber6: TextView

    private var answerNumber = arrayListOf<TextView>()

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private val quiCountModel: QuiCountModel by lazy {
        ViewModelProvider(this)[QuiCountModel::class.java]
    }

    private val quizAnswerModel: QuizAnswerModel by lazy {
        ViewModelProvider(this)[QuizAnswerModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "fun onCreate() is called")
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        trueButton = bindingClass.trueButton
        falseButton = bindingClass.falseButton
        nextButton = bindingClass.nextButton
        prevButton = bindingClass.prevButton
        textQuestionView = bindingClass.questionTextView
        textResult = bindingClass.textResult
        answerNumber1 = bindingClass.answerNumber1
        answerNumber2 = bindingClass.answerNumber2
        answerNumber3 = bindingClass.answerNumber3
        answerNumber4 = bindingClass.answerNumber4
        answerNumber5 = bindingClass.answerNumber5
        answerNumber6 = bindingClass.answerNumber6
        textResult.visibility = View.GONE

        answerNumber.add(answerNumber1)
        answerNumber.add(answerNumber2)
        answerNumber.add(answerNumber3)
        answerNumber.add(answerNumber4)
        answerNumber.add(answerNumber5)
        answerNumber.add(answerNumber6)

        trueButton.setOnClickListener {
            if(!quizViewModel.checkAnswerFromUser()){
                quiCountModel.addAnswerFromUser()
                checkAnswer(true)

            }
        }

        falseButton.setOnClickListener {
            if(!quizViewModel.checkAnswerFromUser()){
                quiCountModel.addAnswerFromUser()
                checkAnswer(false)
            }
        }

        nextButton.setOnClickListener {
            updateAddQuestionAndIndex()
        }

        prevButton.setOnClickListener {
            updateSubQuestionAndIndex()
        }

        textQuestionView.setOnClickListener {
            updateAddQuestionAndIndex()
        }

        checkAnswerColor(quizViewModel.currentIndex)

        updateQuestion()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "fun onResume() is called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "fun onStop() is called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "fun onStop() is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "fun onDestroy() is called")
    }

    private fun updateAddQuestionAndIndex() {
        if(quiCountModel.answerCounting != 6.0) {
            quizViewModel.moveToNext()
            updateQuestion()
        }
    }

    private fun updateSubQuestionAndIndex() {
        quizViewModel.moveToPrev()
        updateQuestion()
    }

//    private fun checkAnswerColor(textId: Int) {
//        if(quizAnswerModel.checkColorTextView(textId) == 1) {
//
//        }
//    }

    private fun updateQuestion() {
        val textQuestionResId = quizViewModel.currentQuestionText
        textQuestionView.setText(textQuestionResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if(userAnswer == correctAnswer) {
            recordAnswer(true)
        } else {
            recordAnswer(false)

        }

    }

    private fun recordAnswer(answer: Boolean) {
        quizViewModel.changeAnswerFromUser()
        val messageResId: Int
        if(answer) {
            messageResId = R.string.correct_toast
            quiCountModel.addCorrectAnswer()
            quizAnswerModel.changeColorTextView(quizViewModel.currentIndex, true)
        } else {
            messageResId = R.string.incorrect_toast
            quizAnswerModel.changeColorTextView(quizViewModel.currentIndex, false)
        }
        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
        if(quiCountModel.answerCounting == 6.0) {
            endQuiz()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun endQuiz() {
        textResult.visibility = View.VISIBLE
        textResult.text = "Result ${quiCountModel.calculatePercentCorrectAnswer()}%"
    }


}