package com.example.bmicalculator

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

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
    private lateinit var cvVisibility: CardView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        weightText = findViewById(R.id.etWeight)
        heightText = findViewById(R.id.etHeight)
        result = findViewById(R.id.tvResultIndex)
        description = findViewById(R.id.tvResult)
        info = findViewById(R.id.tvInfo)
        calculateBtn = findViewById(R.id.btCalculate)
        clearBtn = findViewById(R.id.btnClear)
        heightSpinner = findViewById(R.id.heightSpinner)
        weightSpinner = findViewById(R.id.weightSpinner)
        cvVisibility = findViewById(R.id.cvResult)
        
        // Clear button functionality
        clearBtn.setOnClickListener {
            weightText.text.clear()
            heightText.text.clear()
            result.text = ""
            description.text = ""
            info.text = ""
            cvVisibility.visibility = View.GONE
        }
        
        // Add spinner listeners to dynamically update hints
        weightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (parent.getItemAtPosition(position).toString()) {
                    "Kg" -> weightText.hint = "e.g.,65.52kg"
                    "Pounds" -> weightText.hint = "e.g.,142.32lb"
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        
        heightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                when (parent.getItemAtPosition(position).toString()) {
                    "Ft/In" -> heightText.hint = "e.g.,5'8\""
                    "Cm" -> heightText.hint = "e.g.,172.20cm"
                    "M" -> heightText.hint = "e.g.,1.72m"
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        
        // Calculate button action
        calculateBtn.setOnClickListener {
            val weightStr = weightText.text.toString()
            val heightStr = heightText.text.toString()
            
            if (validateInput(weightStr, heightStr)) {
                var weight = weightStr.toFloat()
                val heightUnit = heightSpinner.selectedItem.toString()
                
                // Convert weight based on selected unit
                when (weightSpinner.selectedItem.toString()) {
                    "Pounds" -> weight *= 0.453592f // Convert pounds to kilograms
                }
                
                // Convert height based on selected unit
                var height = 0f
                when (heightUnit) {
                    "Ft/In" -> {
                        val feet = heightStr.toFloat()
                        val inches = (feet * 12).toFloat()
                        val cm = (inches * 2.54).toFloat()
                        height = cm / 100 // Convert to meters
                    }
                    
                    "Cm" -> height = heightStr.toFloat() / 100 // Convert cm to meters
                    "M" -> height = heightStr.toFloat() // Already in meters
                }
                
                if (height > 0) {
                    val bmi = calculateBMI(weight, height)
                    showBMIResult(bmi)
                } else {
                    heightText.error = "Enter a valid height"
                }
            }
        }
    }
    
    // Validate input fields
    private fun validateInput(
            weightStr: String,
            heightStr: String
    ): Boolean {
        if (weightStr.isEmpty() && heightStr.isEmpty()) {
            weightText.error = "Enter valid weight values"
            heightText.error = "Enter valid height values"
            return false
        } else if (weightStr.isEmpty()) {
            weightText.error = "Enter valid weight values"
            return false
        } else if (heightStr.isEmpty()) {
            heightText.error = "Enter valid height values"
            return false
        }
        return true
    }
    
    // BMI calculation formula
    private fun calculateBMI(
            weight: Float,
            height: Float
    ): Float {
        return weight / (height * height)
    }
    
    // Display BMI result
    private fun showBMIResult(bmi: Float) {
        cvVisibility.visibility = View.VISIBLE
        
        result.text = String.format("%.2f", bmi)
        
        var resultText = ""
        var color = 0
        
        when {
            bmi < 18.50 -> {
                resultText = "You are Underweight"
                color = R.color.under_weight
                info.text = getString(R.string.underweight)
            }
            
            bmi in 18.50..24.99 -> {
                resultText = "Healthy"
                color = R.color.dark_green
                info.text = getString(R.string.normal)
            }
            
            bmi in 25.00..29.99 -> {
                resultText = "Overweight"
                color = R.color.OVERWEIGHT
                info.text = getString(R.string.overweight)
            }
            
            bmi > 29.99 -> {
                resultText = "Obese"
                color = R.color.Obese
                info.text = getString(R.string.obese)
            }
        }
        
        description.setTextColor(ContextCompat.getColor(this, color))
        info.setTextColor(ContextCompat.getColor(this, color))
        description.text = resultText
    }
}
