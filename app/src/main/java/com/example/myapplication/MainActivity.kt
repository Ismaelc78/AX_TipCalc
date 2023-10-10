package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    // lateinit initializes variable before use
    lateinit var tipCalc: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tipCalc = ActivityMainBinding.inflate(layoutInflater)
        setContentView(tipCalc.root)
        tipCalc.calculate.setOnClickListener{ tipCalculator() }
        tipCalc.cancel.setOnClickListener{ resetView() }
    }

    private fun resetView() {
        //startActivity(Intent(this, MainActivity::class.java)) //Just restarts app, not reset view
        tipCalc.totalAmount.text = null
        tipCalc.taxAmount.text = null
        tipCalc.totalDisplayed.text = "$0.00"
        tipCalc.tipDisplayed.text = "$0.00"
        tipCalc.tipOptions.clearCheck()
        tipCalc.tipOptions.check(R.id.zero_percent)
    }

    private fun tipCalculator() {
        val percent = when (tipCalc.tipOptions.checkedRadioButtonId) {
            R.id.five_percent -> 0.05
            R.id.ten_percent -> 0.10
            R.id.twenty_percent -> 0.20
            else -> 0.00
        }
        var totalAmount = 0.00
        if (!tipCalc.totalAmount.text.isBlank()){
            totalAmount = tipCalc.totalAmount.text.toString().toDouble()
        }
        var taxAmount = 0.00
        if(!tipCalc.taxAmount.text.isBlank()){
            taxAmount = tipCalc.taxAmount.text.toString().toDouble()
        }
        var tempTip = totalAmount * percent
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_UP
        tempTip = df.format(tempTip).toDouble()

        tipCalc.tipDisplayed.text = NumberFormat.getCurrencyInstance().format(tempTip).toString()
        val grandTotal = df.format(totalAmount + taxAmount + tempTip).toDouble()
        tipCalc.totalDisplayed.text = NumberFormat.getCurrencyInstance().format(grandTotal).toString()
    }


}


