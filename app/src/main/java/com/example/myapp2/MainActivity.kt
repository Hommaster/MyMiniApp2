package com.example.myapp2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.myapp2.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityMainBinding

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var textQuestionView: TextView

    private val questionBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_ocean, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true)
    )

    private var currentIndex = 0

    private var answerCounting = 0
    private var correctAnswerInt:Int = 0
    private var incorrectAnswerInt:Int = 0


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

        val answerNumber = listOf(
            bindingClass.answerNumber1,
            bindingClass.answerNumber2,
            bindingClass.answerNumber3,
            bindingClass.answerNumber4,
            bindingClass.answerNumber5,
            bindingClass.answerNumber6
        )

        trueButton.setOnClickListener {
            if(!questionBank[currentIndex].userAnswer){
                answerNumber[currentIndex]?.setBackgroundColor(Color.GREEN)
                answerCounting += 1
                checkAnswer(true)

            }
        }

        falseButton.setOnClickListener {
            if(!questionBank[currentIndex].userAnswer){
                answerNumber[currentIndex]?.setBackgroundColor(Color.RED)
                answerCounting +=1
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
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun updateSubQuestionAndIndex() {
        currentIndex = if(currentIndex > 0){
            (currentIndex - 1)
        } else{
            0
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val textQuestionResId = questionBank[currentIndex].textId
        textQuestionView.setText(textQuestionResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        if(userAnswer == correctAnswer) {
            recordAnswer(true)
        } else {
            recordAnswer(false)
        }

    }

    private fun recordAnswer(answer: Boolean) {
        questionBank[currentIndex].userAnswer = true
        val messageResId: Int
        if(answer) {
            messageResId = R.string.correct_toast
            correctAnswerInt += 1
        } else {
            messageResId = R.string.incorrect_toast
            incorrectAnswerInt += 1
        }
        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
        if(answerCounting == 6) {
            endQuiz()
        }
    }

    private fun endQuiz() {
        val countAnswer = (100/answerCounting) * correctAnswerInt
        textQuestionView.text = "Result $countAnswer%"
    }


}