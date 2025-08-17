package kr.co.kimga.fdsevaldemo.pipeline

import kr.co.kimga.fdsevaldemo.config.KafkaProperties
import kr.co.kimga.fdsevaldemo.config.SerdeConfig
import kr.co.kimga.fdsevaldemo.model.AggregatedTransaction
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.service.RuleEvaluator
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.TimeWindows
import org.springframework.stereotype.Component
import java.time.Duration


@Component
class AggregatePipeline(
    override val serdeConfig: SerdeConfig,
    override val kafkaProperties: KafkaProperties,
    override val evaluator: RuleEvaluator
) : EvaluatePipeline {

    override fun build(builder: StreamsBuilder) {
        val transactionSerde = serdeConfig.specificAvroSerde<TransactionEvent>()
        val aggregateSerde = serdeConfig.specificAvroSerde<AggregatedTransaction>()
        val keySerde = serdeConfig.stringSerde()

        builder.stream<String, TransactionEvent>("transactions")
            .groupBy({ _, event -> "${event.accountId}-${event.type}" }, Grouped.with(keySerde, transactionSerde))
            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(1)))
            .aggregate(
                { AggregatedTransaction("", null, 0L, 0L) },
                { _, event, agg -> AggregatedTransaction(
                    event.accountId, event.type,
                    agg.priceSum + event.price,
                    agg.quantitySum + event.quantity
                ) },
                Materialized.with(Serdes.String(), aggregateSerde)
            )
            .filter({ _, agg ->
                evaluator.matchesAggregation(agg)
            })
            .toStream()
            .mapValues { agg -> "userType priceSum=${agg.priceSum}, quantitySum=${agg.quantitySum}" }
            .to("fds-alerts-user-type")
    }
}