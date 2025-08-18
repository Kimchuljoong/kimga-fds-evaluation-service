package kr.co.kimga.fdsevaldemo.model

data class AggregatedTransaction(
    val accountId: String,
    val type: TransactionEventType,
    val priceSum: Long,
    val quantitySum: Long
)