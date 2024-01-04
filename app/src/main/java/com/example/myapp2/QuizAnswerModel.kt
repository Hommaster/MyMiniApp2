package com.example.myapp2

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class QuizAnswerModel: ViewModel() {

    private val textAnswerNumber = listOf(
        AnswerNumber(R.id.answerNumber1, Color.WHITE),
        AnswerNumber(R.id.answerNumber2, Color.WHITE),
        AnswerNumber(R.id.answerNumber3, Color.WHITE),
        AnswerNumber(R.id.answerNumber4, Color.WHITE),
        AnswerNumber(R.id.answerNumber5, Color.WHITE),
        AnswerNumber(R.id.answerNumber6, Color.WHITE)
    )

    var currentIndexAnswer = 0

    val answerQuestionTextView: Int get() = textAnswerNumber[currentIndexAnswer].textNumber

    val answerQuestionColor: Int get() = textAnswerNumber[currentIndexAnswer].colorId

    fun moveToNextAnswer() {
        currentIndexAnswer = (currentIndexAnswer + 1) % textAnswerNumber.size
    }

    fun moveToPrevAnswer() {
        currentIndexAnswer = if(currentIndexAnswer > 0){
            (currentIndexAnswer - 1)
        } else{
            0
        }
    }

    fun changeColorToGreen() {
        textAnswerNumber[currentIndexAnswer].colorId = Color.GREEN
    }

    fun changeColorToRed() {
        textAnswerNumber[currentIndexAnswer].colorId = Color.RED
    }


}