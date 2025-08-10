package kr.co.kimga.fdsevaldemo.model

import java.time.Instant

data class TransactionEvent(
    val transactionId: String,
    val accountId: String,
    val type: TransactionEventType,
    val quantity: Long,
    val price: Long,
    val timestamp: Instant
)
