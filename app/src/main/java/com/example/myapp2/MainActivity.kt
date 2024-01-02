package com.example.myapp2

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

        trueButton.setOnClickListener {
            if(!questionBank[currentIndex].userAnswer){
                checkAnswer(true)
            }
        }

        falseButton.setOnClickListener {
            if(!questionBank[currentIndex].userAnswer){
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
        questionBank[currentIndex].userAnswer = true
        val messageResId = if(userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
    }


}