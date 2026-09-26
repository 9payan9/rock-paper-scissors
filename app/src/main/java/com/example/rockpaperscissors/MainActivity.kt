package com.example.rockpaperscissors

import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var playerScore = 0
    private var computerScore = 0
    private var draws = 0

    private lateinit var resultText: TextView
    private lateinit var scoreText: TextView
    private lateinit var computerText: TextView

    private val choices = listOf("سنگ", "کاغذ", "قیچی")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.rgb(18, 18, 18))
        }

        fun tv(text: String, size: Float): TextView = TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(8, 16, 8, 16)
        }

        root.addView(tv("✊  سنگ کاغذ قیچی  ✌️", 28f))
        root.addView(tv("انتخابت را بزن", 18f))

        scoreText = tv("بازیکن 0   |   کامپیوتر 0   |   مساوی 0", 16f)
        root.addView(scoreText)

        computerText = tv("انتخاب کامپیوتر: ؟", 20f)
        root.addView(computerText)

        resultText = tv("بازی را شروع کن!", 24f)
        root.addView(resultText)

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        choices.forEach { choice ->
            val b = Button(this).apply {
                text = when(choice) {
                    "سنگ" -> "✊\nسنگ"
                    "کاغذ" -> "✋\nکاغذ"
                    else -> "✌️\nقیچی"
                }
                textSize = 16f
                setOnClickListener { play(choice) }
            }
            row.addView(b, LinearLayout.LayoutParams(0, 150, 1f).apply {
                setMargins(8, 8, 8, 8)
            })
        }
        root.addView(row)

        val reset = Button(this).apply {
            text = "شروع دوباره"
            textSize = 16f
            setOnClickListener {
                playerScore = 0
                computerScore = 0
                draws = 0
                updateScore()
                computerText.text = "انتخاب کامپیوتر: ؟"
                resultText.text = "بازی را شروع کن!"
            }
        }
        root.addView(reset)

        setContentView(root)
    }

    private fun play(player: String) {
        val computer = choices[Random.nextInt(choices.size)]
        computerText.text = "انتخاب کامپیوتر: $computer"

        when {
            player == computer -> {
                draws++
                resultText.text = "مساوی! 🤝"
            }
            (player == "سنگ" && computer == "قیچی") ||
            (player == "کاغذ" && computer == "سنگ") ||
            (player == "قیچی" && computer == "کاغذ") -> {
                playerScore++
                resultText.text = "بردی! 🎉"
            }
            else -> {
                computerScore++
                resultText.text = "کامپیوتر برد! 🤖"
            }
        }
        updateScore()
    }

    private fun updateScore() {
        scoreText.text = "بازیکن $playerScore   |   کامپیوتر $computerScore   |   مساوی $draws"
    }
}
