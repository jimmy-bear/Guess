package com.example.guess

import java.util.*


const val MINVALUE=0
const val MAXVALUE=100
class SecretGame (){
    init {
        resetGame()
    }

    var secret=0
    var time=0
    var isEndGame=false
    var minValue=MINVALUE
    var maxValue=MAXVALUE
    val ERRORNUMBER= MAXVALUE+1

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
        secret= Random().nextInt(MAXVALUE)+1
        time=0
        minValue=MINVALUE
        maxValue=MAXVALUE
        isEndGame=false
    }

}