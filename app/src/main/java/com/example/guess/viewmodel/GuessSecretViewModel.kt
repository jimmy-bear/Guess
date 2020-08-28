package com.example.guess.viewmodel

import androidx.lifecycle.MutableLiveData


class GuessSecretViewModel : BaseViewModel(){
    override var times=MutableLiveData<Int>()
    override var secret: Int=0
    override var isEndGame: Boolean=false

    var min=0
    var max=100
    init {
        reset()
    }
    override fun guess(number: Int): Result {
        if(number in min..max) {
            val t=times.value!!+1
            times.value=t
            when{
                number==secret->{
                    isEndGame = true
                    return Result.Bingo
                }
                number>secret->{
                    return Result.Max
                }
                else->{
                    return Result.Min
                }
            }
        }else {
            return Result.Error
        }
    }

    override fun reset() {
        secret=(min..max).random()
        times.value=0
        isEndGame=false
    }

    override fun setLevel(level: Int) {
        when(level){
            0->max= level1
            1->max= level2
            2->max= level3
        }
        reset()
    }
}