package kr.co.kimga.fdsevaldemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConfigurationProperties(prefix = "app.kafka.topics")
class KafkaProperties {
    lateinit var transactions: String
    lateinit var alerts: String
}