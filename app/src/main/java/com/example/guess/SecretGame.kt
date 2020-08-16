package com.example.guess

import java.util.*

class SecretGame (){
    init {
        resetGame()
    }
    var secret=Random().nextInt(10)+1
    var time=0
    var isEndGame=false
    fun diff(number:Int):Int{
        time++
        if (number==secret) {
            isEndGame = true
        }
        return number-secret
    }
    fun getMessage(diff:Int):String{
        return if (diff > 0) {
            "Guess Smailler"
        } else if (diff < 0) {
            "Guess Bigger"
        }else {
            "You got it"
        }

    }
    fun resetGame(){
        secret= Random().nextInt(10)+1
        time=0
        isEndGame=false
    }

}