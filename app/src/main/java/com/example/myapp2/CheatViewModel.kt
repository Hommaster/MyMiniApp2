package com.example.myapp2

import androidx.lifecycle.ViewModel

class CheatViewModel: ViewModel() {

    private val bankCheater = listOf(
        CheatAnswer(0, false, 3),
        CheatAnswer(0, false, 2),
        CheatAnswer(0, false, 1),
        CheatAnswer(0, false, 0),
    )

    var currentHints = 0

    val getCheaterAnswer: Boolean get() = bankCheater[0].cheatAnswerFromCheatActivity
    val getHints: Int get() = bankCheater[currentHints].hintsNumber

    fun changeCheater() {
        bankCheater[0].cheatAnswerFromCheatActivity = true
    }

    fun changeHints() {
        currentHints += 1
    }

}