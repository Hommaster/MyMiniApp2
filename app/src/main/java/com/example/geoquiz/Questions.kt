package com.example.geoquiz

import androidx.annotation.StringRes

data class Questions(@StringRes val textId: Int, val answer: Boolean, var userAnswer: Boolean = false, var isCheatAnswer: Boolean = false) {

}
