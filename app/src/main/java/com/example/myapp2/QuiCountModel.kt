package com.example.myapp2

import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class QuiCountModel: ViewModel() {

    var answerCounting:Double = 0.0
    private var correctAnswer:Double = 0.0

    fun addCorrectAnswer() {
        if(answerCounting < 7) {
            correctAnswer += 1.0
        }
    }

    fun addAnswerFromUser() {
        answerCounting += 1.0
    }

    fun calculatePercentCorrectAnswer(): Double {
        return (((100/answerCounting) * correctAnswer) * 100).roundToInt() / 100.0
    }

    var currentHints = 3

    fun changeHints() {
        currentHints -= 1
    }

}