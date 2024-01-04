package com.example.myapp2

import androidx.lifecycle.ViewModel

class QuizAnswerModel: ViewModel() {

    private val textAnswerNumber = listOf(
        AnswerNumber(R.id.answerNumber1, 2),
        AnswerNumber(R.id.answerNumber2, 2),
        AnswerNumber(R.id.answerNumber3, 2),
        AnswerNumber(R.id.answerNumber4, 2),
        AnswerNumber(R.id.answerNumber5, 2),
        AnswerNumber(R.id.answerNumber6,2)
    )

    fun changeColorTextView(textId: Int, color: Boolean) {
        textAnswerNumber[textId].color = if(color) {
            1
        } else {
            0
        }
    }

    fun checkColorTextView(textId: Int): Int {
        val color = textAnswerNumber[textId].color
        if(color == 1) {
            return 1
        }
        if(color == 0) {
            return 0
        } else {
            return 2
        }
    }

}