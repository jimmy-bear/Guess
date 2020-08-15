package com.example.guess

fun main() {
//    println("Hello world!")
//    val height:Float=1.76f
//    val weight:Float=71.0f
    val p1=Person()
    println(p1.BMI(1.76f,71.0f))
    val p2=Person()
    println(p2.BMI(1.80f,80.0f))
}
class Person() {

    fun hello(){
        println("HelloWorld!")
    }
    fun BMI(h: Float, w: Float):Float{
        val bmi=w/(h*h)
        return bmi
    }
}