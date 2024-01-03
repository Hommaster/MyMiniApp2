package com.example.myapp2

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

    private val questionBank = listOf(
        Questions(R.string.question_australia, true),
        Questions(R.string.question_ocean, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_americas, true),
        Questions(R.string.question_asia, true)
    )

    var currentIndex = 0

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer

    val currentQuestionText: Int get() = questionBank[currentIndex].textId

    fun changeAnswerFromUser() {
        questionBank[currentIndex].userAnswer = true
    }

    fun checkAnswerFromUser(): Boolean {
        return questionBank[currentIndex].userAnswer
    }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = if(currentIndex > 0){
            (currentIndex - 1)
        } else{
            0
        }
    }

}