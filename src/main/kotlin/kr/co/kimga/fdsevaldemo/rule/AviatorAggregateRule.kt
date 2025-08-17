package kr.co.kimga.fdsevaldemo.rule

import com.googlecode.aviator.AviatorEvaluator
import kr.co.kimga.fdsevaldemo.model.AggregatedResult

class AviatorAggregateRule(
    override val name: String,
    override val description: String,
    private val expression: String
) : Rule<AggregatedResult> {
    override fun evaluate(event: AggregatedResult): Boolean {
        val context = toContext(event)
        return AviatorEvaluator.execute(expression, context) as? Boolean ?: false
    }

    private fun toContext(event: AggregatedResult): Map<String, Any> {
        return mapOf(
            "accountId" to event.accountId,
            "count" to event.count,
            "windowStart" to event.windowStart,
            "windowEnd" to event.windowEnd,
        )
    }
}