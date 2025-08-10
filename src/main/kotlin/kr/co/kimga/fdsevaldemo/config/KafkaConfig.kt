package kr.co.kimga.fdsevaldemo.config

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig(val kafkaProperties: KafkaProperties) {

    @Bean
    fun kafkaProducer(): KafkaProducer<String, Any> {
        return KafkaProducer(kafkaProperties.producerProperties())
    }

    @Bean
    fun kafkaConsumer(): KafkaConsumer<String, Any> {
        val consumer = KafkaConsumer<String, Any>(kafkaProperties.consumerProperties())
        consumer.subscribe(listOf(kafkaProperties.topic))
        return consumer
    }
}