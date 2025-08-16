package kr.co.kimga.fdsevaldemo.config

import jakarta.annotation.PostConstruct
import kr.co.kimga.fdsevaldemo.pipeline.EvaluatePipeline
import org.apache.kafka.streams.StreamsBuilder
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaStreamConfig(
    private val evaluatePipelines: List<EvaluatePipeline>,
    private val streamsBuilder: StreamsBuilder
) {

    @PostConstruct
    fun configPipeline() {
        evaluatePipelines.forEach { pipeline ->
            pipeline.build(streamsBuilder)
        }
    }
}