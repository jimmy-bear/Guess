package com.example.guess.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val level1:Int=100
const val level2:Int=500
const val level3:Int=1000

abstract class BaseViewModel:ViewModel() {
    abstract val times:MutableLiveData<Int>
    abstract val secret:Int
    abstract val isEndGame:Boolean
    abstract fun guess(number:Int): Result
    abstract fun reset()
    abstract fun setLevel(level: Int)
}
enum class Result{
    Bingo,Max,Min,Error
}