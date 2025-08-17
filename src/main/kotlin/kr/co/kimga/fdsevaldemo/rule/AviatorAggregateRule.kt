package kr.co.kimga.fdsevaldemo.rule

import com.googlecode.aviator.AviatorEvaluator
import kr.co.kimga.fdsevaldemo.model.AggregatedTransaction

class AviatorAggregateRule(
    override val name: String,
    override val description: String,
    private val expression: String
) : Rule<AggregatedTransaction> {
    override fun evaluate(event: AggregatedTransaction): Boolean {
        val context = toContext(event)
        return AviatorEvaluator.execute(expression, context) as? Boolean ?: false
    }

    private fun toContext(event: AggregatedTransaction): Map<String, Any> {
        return mapOf(
            "accountId" to event.accountId,
            "type" to event.type,
            "priceSum" to event.priceSum,
            "quantitySum" to event.quantitySum,
        )
    }
}