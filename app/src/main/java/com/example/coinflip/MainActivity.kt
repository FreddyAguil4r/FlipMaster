package com.example.coinflip

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    //Initialize variables and define them later
    private lateinit var coinImage : ImageView
    private lateinit var totalCount : TextView
    private lateinit var headsCount : TextView
    private lateinit var tailsCount : TextView
    private lateinit var headsPercent : TextView
    private lateinit var headsProgressBar : ProgressBar
    private lateinit var tailsPercent : TextView
    private lateinit var tailsProgressBar : ProgressBar
    private lateinit var simNumber : EditText
    private lateinit var simButton : Button

    //Counter variables to keep track of heads, tails, and total flips
    private var heads = 0
    private var tails = 0
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get reference to our switch and buttons
        val simSwitch : SwitchCompat = findViewById(R.id.main_activity_sw_simulate)
        val flipButton : Button = findViewById(R.id.main_activity_bt_flip)
        val resetButton : Button = findViewById(R.id.main_activity_bt_reset)
        simButton = findViewById(R.id.main_activity_bt_simulate)

        //Set listeners for our buttons
        simSwitch.setOnCheckedChangeListener { compoundButton, b -> enableSim(b) }
        flipButton.setOnClickListener { flip() }
        resetButton.setOnClickListener { reset() }
        simButton.setOnClickListener { sim() }

        //Set values to other views
        coinImage = findViewById(R.id.main_activity_iv_coin)
        totalCount = findViewById(R.id.main_activity_tv_total_flips)
        headsCount = findViewById(R.id.main_activity_tv_total_heads)
        tailsCount = findViewById(R.id.main_activity_tv_total_tails)
        headsPercent = findViewById(R.id.main_activity_tv_heads_percent)
        headsProgressBar = findViewById(R.id.main_activity_pb_heads_percent)
        tailsPercent = findViewById(R.id.main_activity_tv_tails_percent)
        tailsProgressBar = findViewById(R.id.main_activity_pb_tails_percent)
        simNumber = findViewById(R.id.main_activity_et_sim_number)
    }

    //Run the coin simulation for a set number of flips
    private fun sim() {
        //Get number to sim and clear EditText
        var numberToSim = 1
        if(simNumber.text.toString() != "") {
           numberToSim = simNumber.text.toString().toInt()
        }

        simNumber.setText("")

        //Run the proper number of flips for the simulation
        for(i in 1..numberToSim){
            flip()
        }

        hideKeyboard()
    }

    //Hide the keyboard
    private fun hideKeyboard(){
        val imn = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(coinImage.windowToken,0)
    }

    private fun reset() {
        //Change the imageView back to default
        coinImage.setImageResource(R.drawable.ic_flip_icon)

        //Set all counters vars back to 0
        total = 0
        heads = 0
        tails = 0

        //Update textViews to show results
        totalCount.text = "Total Flips: $total"
        headsCount.text = "Total Heads: $heads"
        tailsCount.text = "Total Heads: $tails"

        //Update statistics UI
        updateStatistics()
    }

    //Simulate a coin flip
    private fun flip() {
        val randomNumber = (0..1).random()
        //Update based on value
        if(randomNumber == 0){
            update("heads")
        }else {
            update("tails")
        }
    }

    private fun update(coinValue: String) {
        //Set the correct image for our coin flip
        if(coinValue == "heads"){
            heads++
            coinImage.setImageResource(R.drawable.ic_head_icon)
        }else{
            tails++
            coinImage.setImageResource(R.drawable.ic_tails_icon)
        }

        //Increment total flips
        total ++

        //Update textViews to show results
        totalCount.text = "Total Flips: $total"
        headsCount.text = "Total Heads: $heads"
        tailsCount.text = "Total Heads: $tails"

        //Update the statistics
        updateStatistics()
    }

    private fun updateStatistics() {
        var headsPercentResult = 0.0
        var tailsPercentResult = 0.0

        if(total != 0) {
            headsPercentResult = round((heads.toDouble() / total) * 10000) /100
            tailsPercentResult = round((tails.toDouble() / total) * 10000) /100
        }

        //Update textViews for percentages
        headsPercent.text = "Heads: $headsPercentResult %"
        tailsPercent.text = "Tails: $tailsPercentResult %"

        //Update progress bars
        headsProgressBar.progress = headsPercentResult.toInt()
        tailsProgressBar.progress = tailsPercentResult.toInt()
    }

    //Turn on/off simulate mode
    private fun enableSim(onState: Boolean){
        //Get the state of the switch
        if(onState){
            simNumber.visibility = View.VISIBLE
            simButton.visibility = View.VISIBLE
        }else{
            simNumber.visibility = View.INVISIBLE
            simButton.visibility = View.INVISIBLE
        }
    }
}