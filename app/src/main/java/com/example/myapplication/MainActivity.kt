package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    // lateinit initializes variable before use
    private lateinit var tipCalc: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tipCalc = ActivityMainBinding.inflate(layoutInflater)
        setContentView(tipCalc.root)
        tipCalc.calculate.setOnClickListener{ tipCalculator() }
    }

    private fun tipCalculator() {
        val percent = when (tipCalc.tipOptions.checkedRadioButtonId) {
            R.id.five_percent -> 0.05
            R.id.ten_percent -> 0.10
            R.id.twenty_percent -> 0.20
            else -> 0.00
        }
        var tempTip = tipCalc.totalAmount.text.toString().toDouble() * percent
        tempTip = kotlin.math.ceil(tempTip)
        val finalTip = NumberFormat.getCurrencyInstance().format(tempTip)
        tipCalc.tipDisplayed.text = finalTip.toString()
    }


}


