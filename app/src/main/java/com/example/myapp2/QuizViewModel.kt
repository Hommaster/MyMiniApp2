package com.example.myapp2

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {
    init {
        Log.d(TAG, "QuizViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "QuizViewModel instance about to be destroyed")
    }

}