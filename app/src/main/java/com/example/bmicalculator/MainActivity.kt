package com.example.bmicalculator

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {
    private lateinit var weightText: EditText
    private lateinit var heightText: EditText
    private lateinit var description: TextView
    private lateinit var result: TextView
    private lateinit var info: TextView
    private lateinit var calculateBtn: Button
    private lateinit var clearBtn: Button
    private lateinit var weightSpinner: Spinner
    private lateinit var heightSpinner: Spinner
    private lateinit var cvVisibility: CardView // Changed from EditText to CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        weightText = findViewById(R.id.etWeight)
        heightText = findViewById(R.id.etHeight)
        result = findViewById(R.id.tvResultIndex)
        description = findViewById(R.id.tvResult)
        info = findViewById(R.id.tvInfo)
        calculateBtn = findViewById(R.id.btCalculate)
        clearBtn = findViewById(R.id.btnClear)
        heightSpinner = findViewById(R.id.heightSpinner)
        weightSpinner = findViewById(R.id.weightSpinner)
        cvVisibility = findViewById(R.id.cvResult) // Initialize as CardView

        // Clear button functionality
        clearBtn.setOnClickListener {
            weightText.text.clear()
            heightText.text.clear()
            result.text = ""
            description.text = ""
            info.text = ""
            cvVisibility.visibility = View.GONE
        }

        // Calculate button functionality
        calculateBtn.setOnClickListener {
            val weightStr = weightText.text.toString()
            val heightStr = heightText.text.toString()

            if (validateInput(weightStr, heightStr)) {
                val weight = weightStr.toFloatOrNull()
                val height = heightStr.toFloatOrNull()
                if (weight != null && height != null && weight > 0 && height > 0) {
                    when {
                        weightSpinner.selectedItem == "Kg" && heightSpinner.selectedItem == "Cm" -> {
                            val bmi = calculateBMI(weight, height)
                            displayResult(bmi)
                        }

                        weightSpinner.selectedItem == "Pounds" && heightSpinner.selectedItem == "Cm" -> {
                            val res = convertToKg(weight)
                            val bmi = calculateBMI(res, height)
                            displayResult(bmi)
                        }

                        heightSpinner.selectedItem == "M" && weightSpinner.selectedItem == "Pounds" -> {
                            val res = convertToKg(weight)
                            var bmi = res / (height * height)
                            bmi = String.format("%.2f", bmi).toFloat()
                            displayResult(bmi)
                        }

                        heightSpinner.selectedItem == "M" && weightSpinner.selectedItem == "Kg" -> {
                            var bmi = weight / (height * height)
                            bmi = String.format("%.2f", bmi).toFloat()
                            displayResult(bmi)
                        }
                    }
                } else {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun convertToKg(weight: Float): Float {
        return weight * 0.45359237f
    }

    private fun calculateBMI(weight: Float, height: Float): Float {
        val heightValue = height / 100
        var bmi = weight / (heightValue * heightValue)
        bmi = String.format("%.2f", bmi).toFloat()
        return bmi
    }

    private fun validateInput(weight: String?, height: String?): Boolean {
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is Empty", Toast.LENGTH_LONG).show()
                false
            }

            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is Empty", Toast.LENGTH_LONG).show()
                false
            }

            else -> true
        }
    }

    private fun displayResult(bmi: Float) {
        cvVisibility.visibility = View.VISIBLE // Set CardView visibility to visible
        val infoText = "Normal range is 18.50 - 24.99"

        result.text = bmi.toString()
        info.text = infoText

        var resultText = ""
        var color = 0

        when {
            bmi < 18.50 -> {
                resultText = "You are Underweight"
                color = R.color.dark_pink
            }

            bmi in 18.50..24.99 -> {
                resultText = "You are Healthy"
                color = R.color.dark_green
            }

            bmi in 25.00..29.99 -> {
                resultText = "You are Overweight"
                color = R.color.red_700
            }

            bmi > 29.99 -> {
                resultText = "You are Obese"
                color = R.color.dark_red
            }
        }

        description.setTextColor(ContextCompat.getColor(this, color))
        description.text = resultText
    }
}
