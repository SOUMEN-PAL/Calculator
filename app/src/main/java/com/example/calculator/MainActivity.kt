package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var tvinput : TextView? = null
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvinput = findViewById(R.id.output)
    }

    fun onDigit(view: View){
        tvinput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }



    fun onClear(view: View)
    {
        tvinput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    private var lastOperatorPos = -1

    fun onDecimal(view: View)
    {
        var currentInput = tvinput?.text.toString()
        if(lastNumeric && !lastDot && (!currentInput.substring(lastOperatorPos + 1).contains(".")))
        {
            tvinput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View)
    {
        var buttonText = (view as Button).text.toString()
        if(tvinput != null)
        {
            var currentInput = tvinput?.text.toString()
            if(currentInput.isEmpty())
            {
                if(buttonText == "-"){
                    tvinput?.append("-")
                    lastNumeric = false
                    lastDot = false
                }
            }
            else{
                if(currentInput.startsWith("-")){
                    if(!isOperator(currentInput.substringAfter("-"))){
                        tvinput?.append(buttonText)
                        lastNumeric = false
                        lastDot = false
                        lastOperatorPos = currentInput.length - 1
                    }
                }
                else{
                    if(lastNumeric && !isOperator(currentInput)){
                        tvinput?.append(buttonText)
                        lastNumeric = false
                        lastDot = false
                        lastOperatorPos = currentInput.length - 1
                    }
                }
            }
        }
    }

    fun onEquals(view: View)
    {
        var textViewValue = tvinput?.text.toString()
        var prefix = ""
        try{
            if(textViewValue.startsWith("-")){
                prefix = "-"
                textViewValue = textViewValue.substringAfter("-")
            }
            if(textViewValue.contains("-")){
                val splitvalue = textViewValue.split("-")

                var one = splitvalue[0]
                var two = splitvalue[1]
                if(prefix.isNotEmpty()){
                    one = prefix + one
                }
                tvinput?.text = removeZeroAfterDot ((one.toDouble() - two.toDouble()).toString())

            }
            else if(textViewValue.contains("+")){
                val splitvalue = textViewValue.split("+")

                var one = splitvalue[0]
                var two = splitvalue[1]
                if(prefix.isNotEmpty()){
                    one = prefix + one
                }
                var res = one.toDouble() + two.toDouble()
                tvinput?.text = removeZeroAfterDot (res.toString())
            }
            else if(textViewValue.contains("*")){
                val splitvalue = textViewValue.split("*")

                var one = splitvalue[0]
                var two = splitvalue[1]
                if(prefix.isNotEmpty()){
                    one = prefix + one
                }
                tvinput?.text = removeZeroAfterDot ((one.toDouble() * two.toDouble()).toString())
            }else if(textViewValue.contains("/")){
                val splitvalue = textViewValue.split("/")

                var one = splitvalue[0]
                var two = splitvalue[1]
                if(prefix.isNotEmpty()){
                    one = prefix + one
                }
                tvinput?.text =removeZeroAfterDot ((one.toDouble() / two.toDouble()).toString())
            }

        }catch (e:ArithmeticException){
            e.printStackTrace()
        }
    }

    private fun removeZeroAfterDot(res : String) : String{
        var value = res
        if(res.contains(".0")){
            value = res.substring(0 , value.length-2)
        }
        if(res.contains( "-0")){
            value = "0"
        }
        return value
    }

    private fun isOperator(value:String):Boolean
    {
        return value.contains("/")
                || value.contains("*")
                || value.contains("-")
                || value.contains("+")
                || value.contains("%")
    }
}