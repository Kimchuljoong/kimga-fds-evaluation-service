package kr.co.kimga.fdsevaldemo.rule

interface Rule<T> {
    val name: String
    val description: String

    fun evaluate(event: T): Boolean
}