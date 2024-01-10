package com.example.geoquiz

import androidx.lifecycle.ViewModel

class CheatViewModel: ViewModel() {

    private val bankCheater = listOf(
        CheatAnswer(0, false,),

    )

    val getCheaterAnswer: Boolean get() = bankCheater[0].cheatAnswerFromCheatActivity

    fun changeCheater() {
        bankCheater[0].cheatAnswerFromCheatActivity = true
    }

}