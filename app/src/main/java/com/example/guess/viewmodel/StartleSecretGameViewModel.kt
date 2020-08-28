package com.example.guess.viewmodel

import androidx.lifecycle.MutableLiveData


class StartleSecretGameViewModel: BaseViewModel() {

    override var times=MutableLiveData<Int>()
    override var secret: Int=0
    override var isEndGame: Boolean=false

    private var max=100
    private var min=0
    val maxView= MutableLiveData<Int>()
    val minView= MutableLiveData<Int>()
    init {
        reset()
    }

    override fun guess(number: Int): Result {
        val t=times.value!!+1
        times.value=t
        if(number in minView.value!!..maxView.value!!) {
            when{
                number==secret->{
                    isEndGame = true
                    return Result.Bingo
                }
                number>secret->{
                    maxView.value=number
                    return Result.Max
                }
                else->{
                    minView.value=number
                    return Result.Min
                }
            }
        }else {
            return Result.Error
        }
    }


    override fun reset() {
        secret=(min..max).random()
        maxView.value=max
        minView.value=min
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