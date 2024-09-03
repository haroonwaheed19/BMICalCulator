//package com.example.bmicalculator
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        val weightText = findViewById<EditText>(R.id.etWeight)
//        val heightText = findViewById<EditText>(R.id.etHeight)
//        val calculateBtn = findViewById<Button>(R.id.btCalculate)
//
//        calculateBtn.setOnClickListener {
//            var weight = weightText.text.toString().toFloat()
//            val height = heightText.text.toString().toFloat()
//            if(validateInput(weight.toString(),height.toString())) {
//                var heightValue = height / 100
//                var bmi = weight / (heightValue * heightValue)
//                //calculate bmi to only 2 digits after decimal
//                bmi = String.format("%.2f", bmi).toFloat()
//                displayResult(bmi)
//            }
//        }
//
//    }
//
//    private fun validateInput(weight:String?,height:String?):Boolean
//    {
//        return when
//        {
//            weight.isNullOrEmpty() ->
//            {
//                Toast.makeText(this, "Weight is Empty", Toast.LENGTH_LONG).show()
//                return false
//            }
//            height.isNullOrEmpty() ->
//            {
//                Toast.makeText(this, "Height is Empty", Toast.LENGTH_LONG).show()
//                return false
//            }
//            else->
//            {
//                return true
//            }
//        }
//    }
//
//    private fun displayResult(bmi: Float)
//    {
//        val result = findViewById<TextView>(R.id.tvResultIndex)
//        val description = findViewById<TextView>(R.id.tvResult)
//        val info = findViewById<TextView>(R.id.tvInfo)
//
//        val infoText = "Normal range is 18.5 - 24.9"
//
//        result.text = bmi.toString()
//        info.text = infoText
//
//        var resultText = ""
//        var color = 0
//
//        when
//        {
//            bmi <18.50 ->
//            {
//                resultText = "You are UnderWeight"
//                color  = R.color.dark_pink
//
//            }
//            bmi in 18.50..24.99 ->{
//                resultText = "You are Healthy"
//                color = R.color.dark_green
//
//            }
//            bmi in 25.00..29.99 ->{
//                resultText = "You are OverWeight"
//                color = R.color.red_700
//            }
//            bmi > 29.99 ->{
//                resultText = "You are Obese"
//                color = R.color.dark_red
//            }
//
//        }
//
//        description.setTextColor(ContextCompat.getColor(this,color))
//        description.text = resultText
//
//    }
//}



package com.example.bmicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ensure the view with ID 'main' exists in the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val calculateBtn = findViewById<Button>(R.id.btCalculate)

        calculateBtn.setOnClickListener {
            val weightStr = weightText.text.toString()
            val heightStr = heightText.text.toString()

            if (validateInput(weightStr, heightStr)) {
                val weight = weightStr.toFloatOrNull()
                val height = heightStr.toFloatOrNull()

                if (weight != null && height != null && weight > 0 && height > 0) {
                    val heightValue = height / 100
                    var bmi = weight / (heightValue * heightValue)
                    // Calculate BMI to only 2 digits after the decimal
                    bmi = String.format("%.2f", bmi).toFloat()
                    displayResult(bmi)
                } else {
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG).show()
                }
            }
        }
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
        val result = findViewById<TextView>(R.id.tvResultIndex)
        val description = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)

        val infoText = "Normal range is 18.5 - 24.9"

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
