package com.example.guess

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var secretGame=SecretGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        secretGameReset()
        val shareTime=getSharedPreferences("Guess", Context.MODE_PRIVATE)
            .getInt("time",0)
        if(shareTime>0){
            val shareSecret=getSharedPreferences("Guess", Context.MODE_PRIVATE)
                .getInt("secret",Random().nextInt(secretGame.maxValue)+1)
            val shareMin=getSharedPreferences("Guess", Context.MODE_PRIVATE)
                .getInt("min",secretGame.minValue)
            val shareMax=getSharedPreferences("Guess", Context.MODE_PRIVATE)
                .getInt("max",secretGame.maxValue)
            AlertDialog.Builder(this)
                .setMessage("You have Not Complete Game \n\nDo you want continue")
                .setPositiveButton("OK"){
                        dialog, where ->
                    secretGame.secret=shareSecret
                    secretGame.time=shareTime
                    secretGame.minValue=shareMin
                    secretGame.maxValue=shareMax
                    txNumber.text=secretGame.time.toString()
                    txMin.text=shareMin.toString()
                    txMax.text=shareMax.toString()
                }
                .setNeutralButton("Cancel",null)
                .show()
        }
        btnLevel.setOnClickListener {
            var level=0
            AlertDialog.Builder(this)
                .setTitle("Choose Level")
                .setItems(R.array.Level){ _, where ->
                    when(where){
                        0->level=0
                        1->level=1
                        2->level=2
                    }
                    secretGame.setLevel(level)
                    secretGame.resetGame()
                    secretGameReset()
                }
                .show()
        }
        btnReplay.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Are you sure?")
                .setPositiveButton("OK") {
                        dialog, which ->
                    secretGame.resetGame()
                    secretGameReset()
                }
                .setNeutralButton("cancel",null)
                .show()
        }
        btnGuess.setOnClickListener {
            try {
                val number = edNumber.text.toString().toInt()
                val diff = secretGame.diff(number)
                when(secretGame.getMessage(diff)) {
                    "Guess Smailler" -> {
                        secretGame.maxValue=number
                        txMax.text=secretGame.maxValue.toString()
                    }
                    "Guess Bigger" -> {
                        secretGame.minValue=number
                        txMin.text=secretGame.minValue.toString()
                    }
                    "You got it" -> {
                        txBetween.text = number.toString()
                        AlertDialog.Builder(this)
                            .setTitle("congratulation")
                            .setMessage("You got it !")
                            .setPositiveButton("OK",null)
                            .show()
                    }
                    "ErrorNumber" -> {
                        AlertDialog.Builder(this)
                            .setMessage("Please Enter ${secretGame.minValue}-${secretGame.maxValue}")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
                txNumber.text=secretGame.time.toString()

            }catch (e :NumberFormatException){
                //error message
                Toast.makeText(this,"Please Enter ${secretGame.minValue}-${secretGame.maxValue}"
                    ,Toast.LENGTH_LONG)
                    .show()
            }
        }

//        btnGuess.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(view: View?) {
//            }
//        })
    }

    private fun secretGameReset() {
        txNumber.text=secretGame.time.toString()
        txBetween.text="?"
        txMin.text=secretGame.minValue.toString()
        txMax.text=secretGame.maxValue.toString()
    }

    override fun onStop() {
        super.onStop()
        if(!secretGame.isEndGame){
            getSharedPreferences("Guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("time",secretGame.time)
                .putInt("secret",secretGame.secret)
                .putInt("min",secretGame.minValue)
                .putInt("max",secretGame.maxValue)
                .apply()
        }
        else{
            getSharedPreferences("Guess", Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply()
        }
    }


}