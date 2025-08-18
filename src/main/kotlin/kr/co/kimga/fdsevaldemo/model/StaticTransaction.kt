package kr.co.kimga.fdsevaldemo.model

data class StaticTransaction(
    val accountId: String,
    val type: TransactionEventType,
    val price: Long,
    val quantity: Long
)
