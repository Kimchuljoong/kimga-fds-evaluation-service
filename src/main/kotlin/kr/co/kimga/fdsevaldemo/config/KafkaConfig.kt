package kr.co.kimga.fdsevaldemo.config

import org.apache.kafka.clients.producer.KafkaProducer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig(val kafkaProperties: KafkaProperties) {

    @Bean
    fun kafkaProducer(): KafkaProducer<String, Any> {
        return KafkaProducer(kafkaProperties.producerProperties())
    }
}