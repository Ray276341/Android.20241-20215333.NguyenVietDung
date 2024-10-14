package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private var firstNumber: String = ""
    private var secondNumber: String = ""
    private var operator: String? = null
    private var isDecimalUsed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linearlayout) // Ensure this is the correct layout file

        // Initialize the display and buttons
        val displayTextView = findViewById<TextView>(R.id.textView)
        val previousOperationTextView = findViewById<TextView>(R.id.previousOperationTextView) // New TextView
        setNumberClickListener(displayTextView)
        setOperatorClickListener(displayTextView, previousOperationTextView) // Pass the new TextView
        setUtilityClickListener(displayTextView)
    }

    // Update the display based on input
    private fun displayNumber(number: String, displayTextView: TextView) {
        displayTextView.text = number
    }

    // Set up number buttons (0-9) and the decimal button
    private fun setNumberClickListener(displayTextView: TextView) {
        val numberButtons = listOf(
            findViewById<Button>(R.id.button_0),
            findViewById<Button>(R.id.button_1),
            findViewById<Button>(R.id.button_2),
            findViewById<Button>(R.id.button_3),
            findViewById<Button>(R.id.button_4),
            findViewById<Button>(R.id.button_5),
            findViewById<Button>(R.id.button_6),
            findViewById<Button>(R.id.button_7),
            findViewById<Button>(R.id.button_8),
            findViewById<Button>(R.id.button_9)
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                val number = button.text.toString()
                if (operator == null) {
                    firstNumber += number
                    displayNumber(firstNumber, displayTextView)
                } else {
                    secondNumber += number
                    displayNumber(secondNumber, displayTextView)
                }
            }
        }

        val dotButton: Button = findViewById(R.id.button_dot)
        dotButton.setOnClickListener {
            if (operator == null) {
                if (!firstNumber.contains(".")) {
                    firstNumber += if (firstNumber.isEmpty()) "0." else "."
                    displayNumber(firstNumber, displayTextView)
                }
            } else {
                if (!secondNumber.contains(".")) {
                    secondNumber += if (secondNumber.isEmpty()) "0." else "."
                    displayNumber(secondNumber, displayTextView)
                }
            }
        }
    }

    // Set up operator buttons (+, -, x, /)
    private fun setOperatorClickListener(displayTextView: TextView, previousOperationTextView: TextView) {
        val operatorButtons = listOf(
            findViewById<Button>(R.id.button_add),
            findViewById<Button>(R.id.button_minus),
            findViewById<Button>(R.id.button_multiply),
            findViewById<Button>(R.id.button_divide)
        )

        for (button in operatorButtons) {
            button.setOnClickListener {
                if (firstNumber.isNotEmpty()) {
                    operator = button.text.toString()
                    previousOperationTextView.text = "$firstNumber $operator" // Show the previous operation
                    displayNumber(firstNumber, displayTextView)
                }
            }
        }

        val equalButton: Button = findViewById(R.id.button_equals)
        equalButton.setOnClickListener {
            if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && operator != null) {
                val result = calculateResult(firstNumber, secondNumber, operator!!)
                previousOperationTextView.text = "$firstNumber $operator $secondNumber = $result" // Update with the full operation
                displayNumber(result.toString(), displayTextView)
                resetAfterCalculation(result)
            }
        }
    }

    // Set up utility buttons (C, CE, BS, +/-)
    private fun setUtilityClickListener(displayTextView: TextView) {
        val clearButton: Button = findViewById(R.id.button_clear)
        val clearEntryButton: Button = findViewById(R.id.button_ce) // Change to match your XML
        val backspaceButton: Button = findViewById(R.id.button_bs) // Change to match your XML
        val signChangeButton: Button = findViewById(R.id.button_negate) // Change to match your XML

        clearButton.setOnClickListener {
            firstNumber = ""
            secondNumber = ""
            operator = null
            displayNumber("0", displayTextView)
        }

        clearEntryButton.setOnClickListener {
            if (operator == null) {
                firstNumber = ""
            } else {
                secondNumber = ""
            }
            displayNumber("0", displayTextView)
        }

        backspaceButton.setOnClickListener {
            if (operator == null) {
                if (firstNumber.isNotEmpty()) {
                    firstNumber = firstNumber.dropLast(1)
                    displayNumber(if (firstNumber.isNotEmpty()) firstNumber else "0", displayTextView)
                }
            } else {
                if (secondNumber.isNotEmpty()) {
                    secondNumber = secondNumber.dropLast(1)
                    displayNumber(if (secondNumber.isNotEmpty()) secondNumber else "0", displayTextView)
                }
            }
        }

        signChangeButton.setOnClickListener {
            if (operator == null) {
                firstNumber = if (firstNumber.startsWith("-")) firstNumber.drop(1) else "-$firstNumber"
                displayNumber(firstNumber, displayTextView)
            } else {
                secondNumber = if (secondNumber.startsWith("-")) secondNumber.drop(1) else "-$secondNumber"
                displayNumber(secondNumber, displayTextView)
            }
        }
    }

    // Calculation logic
    private fun calculateResult(firstNumber: String, secondNumber: String, operator: String): Double {
        val num1 = firstNumber.toDoubleOrNull() ?: 0.0
        val num2 = secondNumber.toDoubleOrNull() ?: 0.0
        return when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "x" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> 0.0
        }
    }

    // Reset the calculator state after calculation
    private fun resetAfterCalculation(result: Double) {
        firstNumber = result.toString()
        secondNumber = ""
        operator = null
    }
}
