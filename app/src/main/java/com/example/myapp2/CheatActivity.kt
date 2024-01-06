package com.example.myapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp2.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity() {

    private lateinit var bindingClass: ActivityCheatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
    }
}