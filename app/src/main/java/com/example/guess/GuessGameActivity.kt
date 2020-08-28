package com.example.guess

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.guess.viewmodel.GuessSecretViewModel
import com.example.guess.viewmodel.Result
import kotlinx.android.synthetic.main.activity_guess_game.*
import java.lang.NumberFormatException
import kotlin.random.Random

class GuessGameActivity : AppCompatActivity() {
    private lateinit var guessViewModel: GuessSecretViewModel
    val TAG=MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_game)
        guessViewModel= ViewModelProvider(this).get(GuessSecretViewModel::class.java)
        guessViewModel.times.observe(this, androidx.lifecycle.Observer {
            txNumberGuess.text=it.toString()
        })
        getSharedPreferences("Guess", Context.MODE_PRIVATE)
            .getInt("time",0)
            .also {time->
                if (time>0){
                    AlertDialog.Builder(this)
                        .setTitle("Notice")
                        .setMessage("Did you want continue?")
                        .setPositiveButton("Yes"){ _, _ ->
                            getSharedPreferences("Guess", Context.MODE_PRIVATE)
                                .apply {
                                    guessViewModel.secret=getInt("secret", Random.nextInt(100)+1)
                                }
                            guessViewModel.times.value=time
                        }
                        .setNeutralButton("No",null)
                        .show()
                }
            }

        btnLevelGuess.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Choose Level")
                .setItems(R.array.Level){ _, where ->
                    guessViewModel.setLevel(where) }
                .setNeutralButton("cancel",null)
                .show()
        }
        btnReplayGuess.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Are you sure?")
                .setPositiveButton("OK") {
                        _, _ ->
                    guessViewModel.reset()
                    edNumberGuess.setText("")

                }
                .setNeutralButton("cancel",null)
                .show()
        }
        btnGuessGuess.setOnClickListener {
            Log.d(TAG, "guessSecret: ${guessViewModel.secret}")
            try {
                val number = edNumberGuess.text.toString().toInt()
                val message:String=when(guessViewModel.guess(number)) {
                    Result.Max -> {
                        "smaller"
                    }
                    Result.Min -> {
                        "Bigger"
                    }
                    Result.Bingo -> {
                        "You got it"
                    }
                    Result.Error -> {
                        "Please Enter ${guessViewModel.min}-${guessViewModel.max}"
                    }
                }
                AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .show()

            }catch (e : NumberFormatException){
                //error message
                Toast.makeText(this,"Please Enter ${guessViewModel.min}-${guessViewModel.max}"
                    , Toast.LENGTH_LONG)
                    .show()
            }
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

    override fun onStop() {
        super.onStop()
        if(!guessViewModel.isEndGame){
            getSharedPreferences("Guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("time",guessViewModel.times.value!!)
                .putInt("secret",guessViewModel.secret)
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