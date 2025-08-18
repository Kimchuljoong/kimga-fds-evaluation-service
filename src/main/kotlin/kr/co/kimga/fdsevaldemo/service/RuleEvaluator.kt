package kr.co.kimga.fdsevaldemo.service

import kr.co.kimga.fdsevaldemo.model.AggregatedTransaction
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.repository.RuleRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class RuleEvaluator(
    val ruleRepository: RuleRepository
) {
    fun matchesTransaction(event: TransactionEvent) =
        ruleRepository.loadTransactionRules().any { it.evaluate(event) }

    fun matchesAggregation(result: AggregatedTransaction) =
        ruleRepository.loadAggregationRules().any { it.evaluate(result) }
}