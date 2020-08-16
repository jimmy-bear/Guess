package com.example.guess

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException
import java.util.*

class MainActivity : AppCompatActivity() {
    val secretGame=SecretGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val shareSecret=getSharedPreferences("Guess", Context.MODE_PRIVATE)
            .getInt("secret",Random().nextInt(10)+1)

        val shareTime=getSharedPreferences("Guess", Context.MODE_PRIVATE)
            .getInt("time",0)
        if(shareTime>0){
            AlertDialog.Builder(this)
                .setMessage("You have Not Complete Game \n\nDo you want continue")
                .setPositiveButton("OK"){
                        dialog, where ->
                    secretGame.secret=shareSecret
                    secretGame.time=shareTime
                    txNumber.setText(secretGame.time.toString())
                }
                .setNeutralButton("Cancel",null)
                .show()
        }
        btnReplay.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Are you sure?")
                .setPositiveButton("OK") {
                        dialog, which ->
                    secretGame.resetGame()
                    txNumber.setText(secretGame.time.toString())
                }
                .setNeutralButton("cancel",null)
                .show()
        }
        btnGuess.setOnClickListener {
//            getSharedPreferences("secret", Context.MODE_PRIVATE)
////                .edit()
////                .putString("secret1", "$secret")
////                .apply()
            try {
                val number = edNumber.text.toString().toInt()
                if(number<=10&&number>=1) {
                    val diff = secretGame.diff(number)
                    AlertDialog.Builder(this)
                        .setMessage(secretGame.getMessage(diff))
                        .setPositiveButton("OK", null)
                        .show()
                    txNumber.setText(secretGame.time.toString())
                }
                else{
                    AlertDialog.Builder(this)
                        .setMessage("Please Enter 1-10")
                        .setPositiveButton("OK", null)
                        .show()
                }

            }catch (e :NumberFormatException){
                //error message
                Toast.makeText(this,"Please Enter 1-10",Toast.LENGTH_LONG).show()

            }


        }

//        btnGuess.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(view: View?) {
//                TODO("Not yet implemented")
//            }
//        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!secretGame.isEndGame){
            getSharedPreferences("Guess", Context.MODE_PRIVATE)
                .edit()
                .putInt("time",secretGame.time)
                .putInt("secret",secretGame.secret)
                .commit()
        }
        else{
            getSharedPreferences("Guess", Context.MODE_PRIVATE).edit().clear().commit()
        }
    }

}