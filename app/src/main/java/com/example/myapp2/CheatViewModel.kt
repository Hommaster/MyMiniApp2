package com.example.myapp2

import androidx.lifecycle.ViewModel

class CheatViewModel: ViewModel() {

    private val bankCheater = listOf(
        CheatAnswer(0, false),
        CheatAnswer(1, false),
        CheatAnswer(2, false),
        CheatAnswer(3, false),
        CheatAnswer(4, false),
        CheatAnswer(5, false),
    )

    var currentIndexCheater = 0

    fun changeCheater() {
        bankCheater[currentIndexCheater].cheatAnswerFromCheatActivity = true
    }

}