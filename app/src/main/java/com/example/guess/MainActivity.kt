package com.example.guess

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var secret=Random().nextInt(10)+1
        println("secret"+secret)
        btnGuess.setOnClickListener {
            val number=edNumber.text.toString().toInt()
            if(number>secret){
                println("Guess Smailler")
            }
            else if(number<secret){
                println("Guess Bigger")
            }
            else{
                AlertDialog.Builder(this)
                    .setTitle("恭喜猜中")
                    .setPositiveButton("OK"){ dialogInterface, i ->
                        secret=Random().nextInt(10)+1
                    }
            }

        }
//        btnGuess.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(view: View?) {
//                TODO("Not yet implemented")
//            }
//        })
    }
}