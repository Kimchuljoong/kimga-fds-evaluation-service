package kr.co.kimga.fdsevaldemo.config

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaInitConfig(
    private val kafkaProperties: KafkaTopicsProperties,
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        val configs = mapOf(
            AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers
        )
        return KafkaAdmin(configs)
    }

    @Bean
    fun transactionTopic(): NewTopic {
        return NewTopic(
            kafkaProperties.transactions,
            1,
            1.toShort()
        )
    }

    @Bean
    fun staticsAlertTopic(): NewTopic {
        return NewTopic(
            kafkaProperties.staticsAlerts,
            1,
            1.toShort()
        )
    }

    @Bean
    fun aggregateAlertTopic(): NewTopic {
        return NewTopic(
            kafkaProperties.aggregateAlerts,
            1,
            1.toShort()
        )
    }
}