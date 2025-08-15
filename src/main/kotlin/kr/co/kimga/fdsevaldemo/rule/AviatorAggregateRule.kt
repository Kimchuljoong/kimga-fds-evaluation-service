package kr.co.kimga.fdsevaldemo.rule

import com.googlecode.aviator.AviatorEvaluator
import kr.co.kimga.fdsevaldemo.model.AggregatedResult
import kr.co.kimga.fdsevaldemo.util.toContext

class AviatorAggregateRule(
    override val name: String,
    override val description: String,
    private val expression: String
) : Rule<AggregatedResult> {
    override fun evaluate(event: AggregatedResult): Boolean {
        val context = toContext(event)
        return AviatorEvaluator.execute(expression, context) as? Boolean ?: false
    }
}