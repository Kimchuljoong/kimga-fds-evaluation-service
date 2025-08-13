package kr.co.kimga.fdsevaldemo.service

import org.apache.kafka.streams.StreamsBuilder

interface KafkaStreamPipeline {
    fun build(builder: StreamsBuilder) {

    }
}