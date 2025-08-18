package kr.co.kimga.fdsevaldemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.kafka.topics")
class KafkaTopicsProperties {
    lateinit var transactions: String
    lateinit var aggregateAlerts: String
    lateinit var staticsAlerts: String
}