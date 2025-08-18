package kr.co.kimga.fdsevaldemo.repository

import kr.co.kimga.fdsevaldemo.rule.AviatorAggregateRule
import kr.co.kimga.fdsevaldemo.rule.AviatorStaticRule
import org.springframework.stereotype.Component

@Component
class RuleRepository {
    fun loadTransactionRules(): List<AviatorStaticRule> {
        return listOf(
            AviatorStaticRule(
                name = "test-rule",
                description = "Test rule",
                expression = "price > 10000"
            )
        )
    }

    fun loadAggregationRules(): List<AviatorAggregateRule> {
        return listOf(
            AviatorAggregateRule(
                name = "test-aggregate-rule",
                description = "Test Aggregate rule",
                expression = "priceSum >= 15000 && quantitySum > 3"
            )
        )
    }
}