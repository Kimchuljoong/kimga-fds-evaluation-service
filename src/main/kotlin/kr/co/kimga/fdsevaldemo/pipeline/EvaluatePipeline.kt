package kr.co.kimga.fdsevaldemo.pipeline

import jakarta.annotation.PostConstruct
import kr.co.kimga.fdsevaldemo.config.KafkaTopicsProperties
import kr.co.kimga.fdsevaldemo.service.RuleEvaluator
import org.apache.kafka.streams.StreamsBuilder

interface EvaluatePipeline {
    val kafkaTopicsProperties: KafkaTopicsProperties
    val evaluator: RuleEvaluator

    @PostConstruct
    fun build(builder: StreamsBuilder)
}