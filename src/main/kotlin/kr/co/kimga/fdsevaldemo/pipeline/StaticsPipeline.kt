package kr.co.kimga.fdsevaldemo.pipeline

import kr.co.kimga.fdsevaldemo.config.KafkaTopicsProperties
import kr.co.kimga.fdsevaldemo.model.StaticTransaction
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.service.RuleEvaluator
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.kafka.support.serializer.JsonSerde
import org.springframework.stereotype.Component

@Component
class StaticsPipeline(
    override val kafkaTopicsProperties: KafkaTopicsProperties,
    override val evaluator: RuleEvaluator,
) : EvaluatePipeline {

    override fun build(builder: StreamsBuilder) {
        val keySerde = Serdes.String()
        val transactionSerde = JsonSerde(TransactionEvent::class.java)
        val staticTransactionSerde = JsonSerde(StaticTransaction::class.java)

        builder.stream(kafkaTopicsProperties.transactions, Consumed.with(keySerde, transactionSerde))
            .filter { _, event -> evaluator.matchesTransaction(event) }
            .peek { key, event -> println("Transaction matched: $key -> $event") }
            .map { _, event -> KeyValue(null, StaticTransaction(event.accountId, event.type, event.price, event.quantity)) }
            .to(kafkaTopicsProperties.staticsAlerts, Produced.with(null, staticTransactionSerde))
    }
}
