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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math)
        val actionBar = supportActionBar
        actionBar?.title = "Реши задачки"
        val editText2 = findViewById<EditText>(R.id.editTextNumberDecimal2)
        val editText3 = findViewById<EditText>(R.id.editTextNumberDecimal3)
        val editText4 = findViewById<EditText>(R.id.editTextNumberDecimal4)
        val editText5 = findViewById<EditText>(R.id.editTextNumberDecimal5)
        val editText6 = findViewById<EditText>(R.id.editTextNumberDecimal6)
        val editTexts = listOf(editText2, editText3, editText4, editText5, editText6)
        for (i in 2..6) {
            val answer = generateAndDisplayExpression(i)
            generateAnswer(i, answer, editTexts)
        }

    }

    private fun generateAndDisplayExpression(num: Int): Int {
        val a = Random.nextInt(1, 100)
        val b = Random.nextInt(1, 100)
        val baseTextViewId = R.id.textView

        // Генерируем идентификатор, добавляя к нему числовой суффикс
        val textViewId = resources.getIdentifier("textView$num", "id", packageName)

        // Находим TextView по идентификатору
        val textView = findViewById<TextView>(textViewId)
        textView.text = "$a + $b"
        return a+b
    }

    private fun generateAnswer(num: Int, answer: Int, list: List<EditText>) {
        val textViewId = resources.getIdentifier("editTextNumberDecimal$num", "id", packageName)

        // Находим TextView по идентификатору
        val textView = findViewById<TextView>(textViewId)
        textView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.toString().toInt() == answer) {
                    val inputValue = s.toString().toInt()

                    textView.setTextColor(Color.GREEN)
                } else if(s != null) {

                    textView.setTextColor(Color.RED) // Или установите цвет, который вам нужен по умолчанию
                }
                if (list.all { it.currentTextColor == Color.GREEN }) {
                    // Выполняем действие, так как все editText зеленые
                    finish()
                }
            }
        })
    }
}