package com.dreampl.wht

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dreampl.R
import kotlinx.android.synthetic.main.activity_start.*

class StartAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startBtn.setOnClickListener{
            startActivity(Intent(this, Game::class.java))
        }
    }
}