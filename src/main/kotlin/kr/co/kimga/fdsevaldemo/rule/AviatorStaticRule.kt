package kr.co.kimga.fdsevaldemo.rule

import com.googlecode.aviator.AviatorEvaluator
import kr.co.kimga.fdsevaldemo.model.TransactionEvent

class AviatorStaticRule(
    override val name: String,
    override val description: String,
    private val expression: String
) : Rule<TransactionEvent> {
    override fun evaluate(event: TransactionEvent): Boolean {
        val context = toContext(event)
        return AviatorEvaluator.execute(expression, context) as? Boolean ?: false
    }

    private fun toContext(event: TransactionEvent): Map<String, Any> {
        return mapOf(
            "transactionId" to event.transactionId,
            "accountId" to event.accountId,
            "type" to event.type.name,
            "quantity" to event.quantity,
            "price" to event.price,
            "timestamp" to event.timestamp.toString() // Instant -> ISO 문자열
        )
    }
}