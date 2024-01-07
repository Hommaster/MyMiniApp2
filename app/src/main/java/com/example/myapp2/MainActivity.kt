package com.example.myapp2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.myapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityMainBinding

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var textQuestionView: TextView
    private lateinit var textResult: TextView
    private lateinit var answerNumber1: TextView
    private lateinit var answerNumber2: TextView
    private lateinit var answerNumber3: TextView
    private lateinit var answerNumber4: TextView
    private lateinit var answerNumber5: TextView
    private lateinit var answerNumber6: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private val quiCountModel: QuiCountModel by lazy {
        ViewModelProvider(this)[QuiCountModel::class.java]
    }

    private val quizAnswerModel: QuizAnswerModel by lazy {
        ViewModelProvider(this)[QuizAnswerModel::class.java]
    }

    private val resultLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {
            quizViewModel.changeCheaterAnswer()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(Constance.TAG, "fun onCreate() is called")
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val currentIndex = savedInstanceState?.getInt(Constance.KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = bindingClass.trueButton
        falseButton = bindingClass.falseButton
        nextButton = bindingClass.nextButton
        prevButton = bindingClass.prevButton
        cheatButton = bindingClass.cheatButton
        textQuestionView = bindingClass.questionTextView
        textResult = bindingClass.textResult
        answerNumber1 = bindingClass.answerNumber1
        answerNumber2 = bindingClass.answerNumber2
        answerNumber3 = bindingClass.answerNumber3
        answerNumber4 = bindingClass.answerNumber4
        answerNumber5 = bindingClass.answerNumber5
        answerNumber6 = bindingClass.answerNumber6

        textResult.visibility = View.GONE

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

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            resultLaunch.launch(intent)
        }

        updateQuestion()
        updateAnswer()
        endQuiz()
    }

    override fun onResume() {
        super.onResume()
        Log.d(Constance.TAG, "fun onResume() is called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(Constance.TAG, "fun onStop() is called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(Constance.TAG, "fun onStop() is stopped")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(Constance.TAG, "onSaveInstanceState")
        savedInstanceState.putInt(Constance.KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(Constance.TAG, "fun onStop() is called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(Constance.TAG, "fun onDestroy() is called")
    }



    private fun updateAddQuestionAndIndex() {
        if(quiCountModel.answerCounting < 6) {
            quizViewModel.moveToNext()
            quizAnswerModel.moveToNextAnswer()

            updateQuestion()
            updateAnswer()
            endQuiz()
        }
    }

    private fun updateSubQuestionAndIndex() {
        quizViewModel.moveToPrev()
        quizAnswerModel.moveToPrevAnswer()
        updateQuestion()
    }

    private fun updateQuestion() {
        val textQuestionResId = quizViewModel.currentQuestionText
        textQuestionView.setText(textQuestionResId)
    }

    private fun updateAnswer() {
        val textViewAnswerColorResId1 = quizAnswerModel.answerQuestionColor1
        val textViewAnswerColorResId2 = quizAnswerModel.answerQuestionColor2
        val textViewAnswerColorResId3 = quizAnswerModel.answerQuestionColor3
        val textViewAnswerColorResId4 = quizAnswerModel.answerQuestionColor4
        val textViewAnswerColorResId5 = quizAnswerModel.answerQuestionColor5
        val textViewAnswerColorResId6 = quizAnswerModel.answerQuestionColor6
        answerNumber1.setBackgroundColor(textViewAnswerColorResId1)
        answerNumber2.setBackgroundColor(textViewAnswerColorResId2)
        answerNumber3.setBackgroundColor(textViewAnswerColorResId3)
        answerNumber4.setBackgroundColor(textViewAnswerColorResId4)
        answerNumber5.setBackgroundColor(textViewAnswerColorResId5)
        answerNumber6.setBackgroundColor(textViewAnswerColorResId6)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        if(userAnswer == correctAnswer) {
            recordAnswer(true)
        } else {
            recordAnswer(false)

        }
        updateAnswer()
        endQuiz()
    }

    private fun recordAnswer(answer: Boolean) {
        quizViewModel.changeAnswerFromUser()
        val messageResId: Int
        val textViewAnswerColorResId = quizAnswerModel.answerQuestionColor
        if(answer) {
            messageResId = if(quizViewModel.isCheater) {
                R.string.judgment_toast
            } else {
                R.string.correct_toast
            }
            quiCountModel.addCorrectAnswer()
            quizAnswerModel.changeColorToGreen()
            checkAnswerNumber().setBackgroundColor(textViewAnswerColorResId)
        } else {
            messageResId = if(quizViewModel.isCheater) {
                R.string.judgment_toast
            } else {
                R.string.incorrect_toast
            }
            quizAnswerModel.changeColorToRed()
            checkAnswerNumber().setBackgroundColor(textViewAnswerColorResId)
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

    private fun checkAnswerNumber(): TextView {
        when(quizViewModel.currentIndex){
            0 -> return answerNumber1
            1 -> return answerNumber2
            2 -> return answerNumber3
            3 -> return answerNumber4
            4 -> return answerNumber5
            5 -> return answerNumber6
        }
        return answerNumber6
    }


    @SuppressLint("SetTextI18n")
    private fun endQuiz() {
        if(quiCountModel.answerCounting == 6.0) {
            val textResultResId = quizAnswerModel.resultQuestionId
            textResult.setText(textResultResId)
            textResult.text = "${textResult.text} ${quiCountModel.calculatePercentCorrectAnswer()}%"
            textResult.visibility = View.VISIBLE
        }
    }


}