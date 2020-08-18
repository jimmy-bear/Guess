package com.example.guess

import java.util.*
import java.util.logging.Level

const val LEVELE1=100
const val LEVELE2=500
const val LEVELE3=1000


class SecretGame (var min:Int=1,var max:Int=100){
    init {
        resetGame()
    }

    var secret=0
    var time=0
    var isEndGame=false
    var minValue=min
    var maxValue=max
    val ERRORNUMBER= max+1

    fun diff(number: Int):Int{
        //make sure input 1-100
        if(number<=maxValue && number>=minValue) {
            time++

            if (number == secret) {
                isEndGame = true
            }
            return number-secret

        }else {
            return ERRORNUMBER
        }
    }
    fun setLevel(level:Int){
        when(level){
            0->max= LEVELE1
            1->max= LEVELE2
            2->max= LEVELE3
        }
    }
    fun getMessage(dif:Int):String{

        return if (dif > 0 && dif<maxValue) {
            "Guess Smailler"
        } else if (dif < 0) {
            "Guess Bigger"
        }else if(dif==0){
            "You got it"
        }else{
            "ErrorNumber"
        }
    }
    fun resetGame(){
        secret= Random().nextInt(max)+1
        time=0
        minValue=min
        maxValue=max
        isEndGame=false
    }

}