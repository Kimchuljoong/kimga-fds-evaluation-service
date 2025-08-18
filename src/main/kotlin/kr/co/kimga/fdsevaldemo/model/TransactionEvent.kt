package kr.co.kimga.fdsevaldemo.model

data class TransactionEvent(
    val transactionId: String,
    val accountId: String,
    val type: TransactionEventType,
    val quantity: Long,
    val price: Long,
    val timestamp: java.time.Instant
)