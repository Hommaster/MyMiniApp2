package com.example.myapp2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapp2.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private lateinit var bindingClass: ActivityCheatBinding

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this)[CheatViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        answerIsTrue = intent.getBooleanExtra(Constance.EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = bindingClass.answerTextView
        showAnswerButton = bindingClass.showAnswerButton

        showAnswerButton.setOnClickListener {
            val answerTextRes = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerTextRes)
            setAnswerShownResult(true)
        }
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(Constance.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private fun setAnswerShownResult(answerIsTrue: Boolean) {
        val data = Intent()
        quizViewModel.changeCheaterAnswer()
        setResult(Activity.RESULT_OK, data)
    }

}