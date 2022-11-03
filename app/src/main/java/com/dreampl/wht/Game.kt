package com.dreampl.wht

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import com.dreampl.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    val roll = mutableListOf<String>("1", "2", "3")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var one: String = ""
        var two: String = ""
        var three: String = ""

        var first: ImageView = findViewById(R.id.first)
        var second: ImageView = findViewById(R.id.second)
        var third: ImageView = findViewById(R.id.third)

        val s: Long = "5".toLong() * 1000

        object : CountDownTimer(s, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                running.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                MaterialAlertDialogBuilder(this@Game)
                    .setTitle("Time's Up")
                    .setCancelable(false)
                    .setPositiveButton("Play again") { dialog, _ ->
                        val preferences = getSharedPreferences("PREF", 0)
                        val editor = preferences.edit()
                        editor.apply()
                        startActivity(Intent(applicationContext, Game::class.java))
                        finish()
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }.start()



        button2.setOnClickListener {
            three = roll.random()
            check(one, first)
            check(three, third)
            check(two, second)
            checkWin(one, two, three)
        }

        button1.setOnClickListener {
            one = roll.random()
            check(one, first)
            check(three, third)
            check(two, second)
            checkWin(one, two, three)
        }
        button3.setOnClickListener {
            two = roll.random()
            check(one, first)
            check(three, third)
            check(two, second)
            checkWin(one, two, three)
        }

    }

    fun check(i: String, im: ImageView) {
        if (i == "1") {
            im.setImageResource(R.drawable.eye)
        } else if (i == "2") {
            im.setImageResource(R.drawable.juke)
        } else if (i == "3") {
            im.setImageResource(R.drawable.pyr)
        }
    }

    fun checkWin(i: String, i1: String, i2: String) {
        if (i == "1" && i1 == "1" && i2 == "1") {
            intentr()
        } else if (i == "2" && i1 == "2" && i2 == "2") {
            intentr()

        } else if (i == "3" && i1 == "3" && i2 == "3") {
            intentr()
        }
    }


    fun intentr() {
        val intent = Intent(this, Game::class.java)
        startActivity(intent)
    }
}