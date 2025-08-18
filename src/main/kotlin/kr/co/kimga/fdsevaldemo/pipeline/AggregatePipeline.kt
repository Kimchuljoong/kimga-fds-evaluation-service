package kr.co.kimga.fdsevaldemo.pipeline


import kr.co.kimga.fdsevaldemo.config.KafkaTopicsProperties
import kr.co.kimga.fdsevaldemo.model.AggregatedTransaction
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.model.TransactionEventType
import kr.co.kimga.fdsevaldemo.service.RuleEvaluator
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.support.serializer.JsonSerde
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class AggregatePipeline(
    override val kafkaTopicsProperties: KafkaTopicsProperties,
    override val evaluator: RuleEvaluator,
    @Value("\${fds.window-size-seconds:5}") private val windowSize: Long
) : EvaluatePipeline {

    override fun build(builder: StreamsBuilder) {
        val transactionSerde = JsonSerde(TransactionEvent::class.java)
        val aggregateSerde = JsonSerde(AggregatedTransaction::class.java)
        val keySerde = Serdes.String()

        builder.stream(kafkaTopicsProperties.transactions, Consumed.with(keySerde, transactionSerde))
            .groupBy({ _, event -> "${event.accountId}-${event.type}" }, Grouped.with(keySerde, transactionSerde))
            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
            .aggregate(
                { AggregatedTransaction("", TransactionEventType.BUY, 0L, 0L) },
                { _, event, agg -> AggregatedTransaction(
                    event.accountId,
                    event.type,
                    agg.priceSum + event.price,
                    agg.quantitySum + event.quantity
                ) },
                Materialized.with(keySerde, aggregateSerde)
            )
            .filter { _, agg ->
                evaluator.matchesAggregation(agg)
            }
            .toStream()
            .selectKey { windowedKey, _ -> windowedKey.key() }
            .peek { _, value ->
                println("AggregatePipeline: value=$value")
            }
            .to(kafkaTopicsProperties.aggregateAlerts, Produced.with(keySerde, aggregateSerde))
    }
}
