package com.example.guess

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guess.viewmodel.Result
import com.example.guess.viewmodel.StartleSecretGameViewModel
import kotlinx.android.synthetic.main.activity_startle_game.*
import kotlin.random.Random

class StartleGameActivity : AppCompatActivity() {

    private lateinit var startleViewModel:StartleSecretGameViewModel
    val TAG=StartleGameActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startle_game)
        startleViewModel=ViewModelProvider(this).get(StartleSecretGameViewModel::class.java)
        startleViewModel.maxView.observe(this, Observer {
            txMaxStartle.text=it.toString()
        })
        startleViewModel.minView.observe(this, Observer {
            txMinStartle.text=it.toString()
        })
        startleViewModel.times.observe(this, Observer {
            txNumberStartle.text=it.toString()
        })
        getSharedPreferences("startle", Context.MODE_PRIVATE)
            .getInt("times",0).also { times->
                if(times>0){
                    AlertDialog.Builder(this)
                        .setMessage("Do you want continue?")
                        .setPositiveButton("Yes") { _, _ ->
                            getSharedPreferences("startle", Context.MODE_PRIVATE)
                                .run {
                                    startleViewModel.maxView.value=getInt("max",100)
                                    startleViewModel.minView.value=getInt("min",0)
                                    startleViewModel.secret=getInt("secret",Random.nextInt(100)+1)
                                }
                            startleViewModel.times.value=times
                        }
                        .setNeutralButton("No", null)
                        .show()
                }
            }

        btnGuessStartle.setOnClickListener {
            Log.d(TAG, "startleSecret: ${startleViewModel.secret}")
            val number=edNumberStartle.text.toString().toInt()
            when(startleViewModel.guess(number)){
                Result.Bingo->{
                    txBetweenStartle.text=number.toString()
                    AlertDialog.Builder(this)
                        .setTitle("Congratulation")
                        .setMessage("You Got it")
                        .setPositiveButton("OK",null)
                        .show()
                }
                Result.Error->{
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Please Enter ${startleViewModel.minView.value}-${startleViewModel.maxView.value}")
                        .setPositiveButton("OK",null)
                        .show()
                }
                else->{

                }
            }
        }
        btnReplayStartle.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Are you sure?")
                .setPositiveButton("OK") {
                        _, _ ->
                    startleViewModel.reset()
                    reset()

                }
                .setNeutralButton("cancel",null)
                .show()
        }
        btnLevelStartle.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Choose Level")
                .setItems(R.array.Level) { _, where ->
                    startleViewModel.setLevel(where)
                    reset()}
                .setNeutralButton("cancel",null)
                .show()
        }
    }

    override fun onStop() {
        super.onStop()
        if (!startleViewModel.isEndGame) {
            getSharedPreferences("startle", Context.MODE_PRIVATE)
                .edit()
                .putInt("max",startleViewModel.maxView.value!!)
                .putInt("min",startleViewModel.minView.value!!)
                .putInt("times",startleViewModel.times.value!!)
                .putInt("secret",startleViewModel.secret)
                .apply()
        }else{
            getSharedPreferences("startle", Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()
        }
    }
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you want to leave?")
            .setNeutralButton("No",null)
            .setPositiveButton("Yes"){ _, _ ->
                finish()
            }.show()
    }
    private fun reset() {
        txBetweenStartle.text="unKnown"
        edNumberStartle.setText("")
    }
}