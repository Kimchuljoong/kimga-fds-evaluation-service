package kr.co.kimga.fdsevaldemo.pipeline

import kr.co.kimga.fdsevaldemo.config.KafkaProperties
import kr.co.kimga.fdsevaldemo.config.SerdeConfig
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.service.RuleEvaluator
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.Produced
import org.springframework.stereotype.Component


@Component
class TransactionPipeline(
    override val serdeConfig: SerdeConfig,
    override val kafkaProperties: KafkaProperties,
    override val evaluator: RuleEvaluator
) : EvaluatePipeline {

    override fun build(builder: StreamsBuilder) {
        val valueSerde = serdeConfig.specificAvroSerde<TransactionEvent>()
        val keySerde = serdeConfig.stringSerde()

        builder.stream(
            kafkaProperties.transactions,
            Consumed.with(keySerde, valueSerde)
        ).filter { _, event ->
            evaluator.matchesTransaction(event)
        }.peek { key, event ->
            println("Transaction matched: $key -> $event")
        }.to(
            kafkaProperties.alerts,
            Produced.with(keySerde, valueSerde)
        )
    }
}