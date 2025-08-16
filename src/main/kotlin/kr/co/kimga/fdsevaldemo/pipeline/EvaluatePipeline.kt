package kr.co.kimga.fdsevaldemo.pipeline

import kr.co.kimga.fdsevaldemo.config.KafkaProperties
import kr.co.kimga.fdsevaldemo.config.SerdeConfig
import kr.co.kimga.fdsevaldemo.service.RuleEvaluator
import org.apache.kafka.streams.StreamsBuilder

interface EvaluatePipeline {
    val serdeConfig: SerdeConfig
    val kafkaProperties: KafkaProperties
    val evaluator: RuleEvaluator

    fun build(builder: StreamsBuilder)
}