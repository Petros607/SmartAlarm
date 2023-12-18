package com.example.smartalarm

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class MathActivity : AppCompatActivity() {
    private val editTexts = mutableListOf<EditText>()
    private val answers = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math)
        val actionBar = supportActionBar
        actionBar?.title = "Реши задачки"
            for (i in 2..6) {
                val editText = findViewById<EditText>(resources.getIdentifier("editTextNumberDecimal$i", "id", packageName))
                editTexts.add(editText)
                generateAndDisplayExpression(i)
                generateAnswer(i, editTexts)
        }

    }

    private fun generateAndDisplayExpression(num: Int) {
        val a = Random.nextInt(1, 100)
        val b = Random.nextInt(1, 100)
        val textView = findViewById<TextView>(resources.getIdentifier("textView$num", "id", packageName))
        answers.add(a + b)
        textView.text = "$a + $b"
    }

    private fun generateAnswer(num: Int, editTexts: List<EditText>) {
        val answer = answers[num-2]

        val editText = findViewById<EditText>(resources.getIdentifier("editTextNumberDecimal$num", "id", packageName))

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    val inputValue = s.toString().toIntOrNull() ?: 0
                    editText.setTextColor(if (inputValue == answer) Color.GREEN else Color.RED)
                    if (editTexts.all { it.currentTextColor == Color.GREEN }) {
                        finish()
                    }
                }
            }
        })
    }
}