package com.example.myapp2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapp2.databinding.ActivityMainBinding
import kotlin.math.roundToInt

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

    var answerNumber = arrayListOf<TextView>()

    private val questionBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_ocean, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true)
    )

    private var currentIndex = 0

    private var answerCounting:Double = 0.0
    private var correctAnswerInt:Double = 0.0
    private var incorrectAnswerInt:Double = 0.0


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
        textResult = bindingClass.textResult!!
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
            if(!questionBank[currentIndex].userAnswer){
                answerCounting += 1
                checkAnswer(true)

            }
        }

        falseButton.setOnClickListener {
            if(!questionBank[currentIndex].userAnswer){
                answerCounting += 1
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
        if(answerCounting != 6.0) {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
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
            answerNumber[currentIndex].setBackgroundColor(Color.GREEN)
        } else {
            messageResId = R.string.incorrect_toast
            incorrectAnswerInt += 1
            answerNumber[currentIndex].setBackgroundColor(Color.RED)
        }
        Toast.makeText(
            this,
            messageResId,
            Toast.LENGTH_SHORT
        ).show()
        if(answerCounting == 6.0) {
            endQuiz()
        }
    }

    private fun endQuiz() {
        val countAnswer = (((100/answerCounting) * correctAnswerInt) * 100).roundToInt() / 100.0
        textResult.visibility = View.VISIBLE
        textResult.text = "Result $countAnswer%"
    }


}