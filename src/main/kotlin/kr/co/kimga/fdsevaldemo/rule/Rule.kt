package kr.co.kimga.fdsevaldemo.rule

import kr.co.kimga.fdsevaldemo.model.TransactionEvent

interface Rule<T> {
    val name: String
    val description: String

    fun evaluate(event: T): Boolean
}