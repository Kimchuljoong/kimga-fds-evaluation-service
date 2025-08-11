package kr.co.kimga.fdsevaldemo.rule

import com.googlecode.aviator.AviatorEvaluator
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.util.toContext

class AviatorStaticRule(
    override val name: String,
    override val description: String,
    private val expression: String
) : Rule<TransactionEvent> {
    override fun evaluate(event: TransactionEvent): Boolean {
        val context = toContext(event)
        return AviatorEvaluator.execute(expression, context) as? Boolean ?: false
    }
}