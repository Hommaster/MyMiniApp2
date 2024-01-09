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
    private lateinit var backMainButton: Button

    private lateinit var bindingClass: ActivityCheatBinding
    private lateinit var currentHintsText: TextView
    private lateinit var hintsNumber: TextView

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
        backMainButton = bindingClass.backToMain
        currentHintsText = bindingClass.currentHints
        hintsNumber = bindingClass.hintsTextView

        showAnswerButton.setOnClickListener {
            if(cheatViewModel.getHints > 0) {
                cheatViewModel.changeCheater()
                cheatViewModel.changeHints()
                textRes()
                setAnswerShownResult()
            }

        }

        backMainButton.setOnClickListener {
            if(cheatViewModel.getCheaterAnswer) {
                setAnswerShownResult()
            } else {
                val data = Intent()
                setResult(Activity.RESULT_CANCELED, data)
            }
            finish()
        }
        callSetAnswerShownResult()
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(Constance.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    private fun setAnswerShownResult() {
        val data = Intent()
        setResult(Activity.RESULT_OK, data)
    }

    private fun textRes() {
        val answerTextRes = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        answerTextView.setText(answerTextRes)
        hintsNumber.text = cheatViewModel.getHints.toString()
    }

    private fun callSetAnswerShownResult() {
        if(cheatViewModel.getCheaterAnswer) {
            setAnswerShownResult()
            textRes()
        }
    }
}