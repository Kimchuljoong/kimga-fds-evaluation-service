package kr.co.kimga.fdsevaldemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.kafka.streams")
class KafkaStreamsProperties {
    lateinit var transaction: StreamsPipelineProperties
    lateinit var statics: StreamsPipelineProperties
    lateinit var aggregate: StreamsPipelineProperties

    data class StreamsPipelineProperties(
        var applicationId: String = "",
        var properties: Map<String, String> = emptyMap()
    )
}
