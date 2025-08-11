package kr.co.kimga.fdsevaldemo.model

data class AggregatedResult(
    val accountId: String,
    val count: Long,
    val windowStart: Long,
    val windowEnd: Long
)