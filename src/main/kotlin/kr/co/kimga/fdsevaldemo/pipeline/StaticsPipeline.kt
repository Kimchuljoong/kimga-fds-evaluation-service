package kr.co.kimga.fdsevaldemo.pipeline

import kr.co.kimga.fdsevaldemo.config.KafkaTopicsProperties
import kr.co.kimga.fdsevaldemo.config.SerdeConfig
import kr.co.kimga.fdsevaldemo.model.StaticTransaction
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.service.RuleEvaluator
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.stereotype.Component


@Component
class TransactionPipeline(
    override val serdeConfig: SerdeConfig,
    override val kafkaTopicsProperties: KafkaTopicsProperties,
    override val evaluator: RuleEvaluator,
) : EvaluatePipeline {

    override fun build(builder: StreamsBuilder) {
        val keySerde = serdeConfig.stringSerde()
        val transactionSerde = serdeConfig.specificAvroSerde<TransactionEvent>()
        val staticTransactionSerde = serdeConfig.specificAvroSerde<StaticTransaction>()

        builder.stream(kafkaTopicsProperties.transactions, Consumed.with(keySerde, transactionSerde))
            .filter { _, event -> evaluator.matchesTransaction(event) }
            .peek { key, event -> println("Transaction matched: $key -> $event") }
            .map { _, event -> KeyValue(null, StaticTransaction(event.accountId, event.type, event.price, event.quantity)) }
            .to(kafkaTopicsProperties.staticsAlerts, Produced.with(null, staticTransactionSerde))
    }
}