package com.example.geoquiz

import androidx.lifecycle.ViewModel


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

    val isCheater: Boolean get() = questionBank[currentIndex].isCheatAnswer

    val currentQuestionAnswer: Boolean get() = questionBank[currentIndex].answer

    val currentQuestionText: Int get() = questionBank[currentIndex].textId

    fun changeAnswerFromUser() {
        questionBank[currentIndex].userAnswer = true
    }

    fun changeCheaterAnswer() {
        questionBank[currentIndex].isCheatAnswer = true
    }

    fun checkAnswerFromUser(): Boolean {
        return questionBank[currentIndex].userAnswer
    }

    fun moveToNext() {
        if(currentIndex == 5){
            currentIndex + 0
        } else {
            currentIndex = (currentIndex + 1)
        }
    }

    fun moveToPrev() {
        currentIndex = if(currentIndex > 0){
            (currentIndex - 1)
        } else{
            0
        }
    }

}