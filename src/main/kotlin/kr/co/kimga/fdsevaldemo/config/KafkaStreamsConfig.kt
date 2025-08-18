package kr.co.kimga.fdsevaldemo.config

import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class KafkaStreamsConfig(
    val streamsProps: KafkaStreamsProperties
) {

    @Bean(name = ["transactionStreamsConfig"])
    fun transactionStreamsConfig(): StreamsConfig {
        val props = Properties().apply {
            putAll(streamsProps.transaction.properties)
            put(StreamsConfig.APPLICATION_ID_CONFIG, streamsProps.transaction.applicationId)
        }
        return StreamsConfig(props)
    }

    @Bean(name = ["aggregateStreamsConfig"])
    fun aggregateStreamsConfig(): StreamsConfig {
        val props = Properties().apply {
            putAll(streamsProps.aggregate.properties)
            put(StreamsConfig.APPLICATION_ID_CONFIG, streamsProps.aggregate.applicationId)
        }
        return StreamsConfig(props)
    }

    @Bean(name = ["transactionBuilder"])
    fun transactionBuilder(): StreamsBuilder = StreamsBuilder()

    @Bean(name = ["aggregateBuilder"])
    fun aggregateBuilder(): StreamsBuilder = StreamsBuilder()
}