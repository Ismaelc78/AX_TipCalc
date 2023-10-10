package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    // lateinit initializes variable before use
    private lateinit var tipCalc: ActivityMainBinding

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
        tipCalc.totalDisplayed.text = getString(R.string.grand_total_displayed)
        tipCalc.tipDisplayed.text = getString(R.string.tip_amount_displayed)
        tipCalc.tipOptions.clearCheck()
        tipCalc.tipOptions.check(R.id.zero_percent)
    }

    private fun tipCalculator() {
        if(!checkInput()){return}
        val percent = when (tipCalc.tipOptions.checkedRadioButtonId) {
            R.id.five_percent -> 0.05
            R.id.ten_percent -> 0.10
            R.id.twenty_percent -> 0.20
            else -> 0.00
        }
        val totalAmount = tipCalc.totalAmount.text.toString().toDouble()
        val taxAmount = tipCalc.taxAmount.text.toString().toDouble()
        var tempTip = totalAmount * percent
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.HALF_UP
        tempTip = df.format(tempTip).toDouble()
        tipCalc.tipDisplayed.text = NumberFormat.getCurrencyInstance().format(tempTip).toString()
        val grandTotal = df.format(totalAmount + taxAmount + tempTip).toDouble()
        tipCalc.totalDisplayed.text = NumberFormat.getCurrencyInstance().format(grandTotal).toString()
    }

    private fun checkInput(): Boolean{
        if (tipCalc.totalAmount.text.isBlank()){
            tipCalc.totalAmount.error = "This field is required"
            return false
        }
        if(tipCalc.taxAmount.text.isBlank()){
            tipCalc.taxAmount.error = "This field is required"
            return false
        }
        return true
    }


}


