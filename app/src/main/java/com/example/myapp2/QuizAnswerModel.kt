package com.example.myapp2

import android.graphics.Color
import androidx.lifecycle.ViewModel


class QuizAnswerModel: ViewModel() {

    private val textAnswerNumber = listOf(
        AnswerNumber(R.id.answerNumber1, Color.WHITE),
        AnswerNumber(R.id.answerNumber2, Color.WHITE),
        AnswerNumber(R.id.answerNumber3, Color.WHITE),
        AnswerNumber(R.id.answerNumber4, Color.WHITE),
        AnswerNumber(R.id.answerNumber5, Color.WHITE),
        AnswerNumber(R.id.answerNumber6, Color.WHITE),
        AnswerNumber(R.string.text_end_quiz, Color.BLUE)
    )

    private var currentIndexAnswer = 0

    val answerQuestionColor1: Int get() = textAnswerNumber[0].colorId
    val answerQuestionColor2: Int get() = textAnswerNumber[1].colorId
    val answerQuestionColor3: Int get() = textAnswerNumber[2].colorId
    val answerQuestionColor4: Int get() = textAnswerNumber[3].colorId
    val answerQuestionColor5: Int get() = textAnswerNumber[4].colorId
    val answerQuestionColor6: Int get() = textAnswerNumber[5].colorId

    val resultQuestionId: Int get() = textAnswerNumber[6].textNumber

    val answerQuestionColor: Int get() = textAnswerNumber[currentIndexAnswer].colorId

    fun moveToNextAnswer() {
        if(currentIndexAnswer == 5){
            currentIndexAnswer + 0
        } else {
            currentIndexAnswer = (currentIndexAnswer + 1)
        }
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