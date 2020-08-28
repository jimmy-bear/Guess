package com.example.guess

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageBGuessMain.setOnClickListener {
            txNoteMain.text="Guess Secret Game Description..."
            Toast.makeText(this,"Long click can enter the game",Toast.LENGTH_SHORT).show()
        }
        imageBGuessMain.setOnLongClickListener {
            startActivity(Intent(this,GuessGameActivity::class.java))
            return@setOnLongClickListener true
        }
        imageBStartleMain.setOnClickListener {
            txNoteMain.text="Startle Secret Game Description..."
            Toast.makeText(this,"Long click can enter the game",Toast.LENGTH_SHORT).show()
        }
        imageBStartleMain.setOnLongClickListener {
            startActivity(Intent(this,StartleGameActivity::class.java))
           return@setOnLongClickListener true
        }
    }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_main, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_leave -> {
                    finish()
                }
            }
            return super.onOptionsItemSelected(item)
        }
    }
