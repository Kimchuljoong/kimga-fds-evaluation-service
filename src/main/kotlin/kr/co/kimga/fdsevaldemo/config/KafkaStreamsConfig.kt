package kr.co.kimga.fdsevaldemo.config

import kr.co.kimga.fdsevaldemo.pipeline.AggregatePipeline
import kr.co.kimga.fdsevaldemo.pipeline.StaticsPipeline
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@EnableConfigurationProperties(KafkaStreamsProperties::class)
class KafkaStreamsConfig(
    @Qualifier("kafkaStreamsProperties") val kafkaStreamsProperties: KafkaStreamsProperties,
) {

    @Bean
    fun aggregateStreamsConfig(): StreamsConfig {
        val props = Properties().apply {
            putAll(kafkaStreamsProperties.aggregate.properties)
            put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaStreamsProperties.aggregate.applicationId)
        }
        return StreamsConfig(props)
    }

    @Bean
    fun staticsStreamsConfig(): StreamsConfig {
        val props = Properties().apply {
            putAll(kafkaStreamsProperties.statics.properties)
            put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaStreamsProperties.statics.applicationId)
        }
        return StreamsConfig(props)
    }

    @Bean
    fun aggregateBuilder(): StreamsBuilder = StreamsBuilder()

    @Bean
    fun staticsBuilder(): StreamsBuilder = StreamsBuilder()

    @Bean
    fun aggregateKafkaStreams(
        aggregatePipeline: AggregatePipeline,
        @Qualifier("aggregateBuilder") builder: StreamsBuilder,
        @Qualifier("aggregateStreamsConfig") streamsConfig: StreamsConfig
    ): KafkaStreams {
        aggregatePipeline.build(builder)
        return KafkaStreams(builder.build(), streamsConfig).apply { start() }
    }

    @Bean
    fun staticsKafkaStreams(
        staticsPipeline: StaticsPipeline,
        @Qualifier("staticsBuilder") builder: StreamsBuilder,
        @Qualifier("staticsStreamsConfig") streamsConfig: StreamsConfig
    ): KafkaStreams {
        staticsPipeline.build(builder)
        return KafkaStreams(builder.build(), streamsConfig).apply { start() }
    }
}