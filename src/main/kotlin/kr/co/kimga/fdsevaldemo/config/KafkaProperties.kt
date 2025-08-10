package kr.co.kimga.fdsevaldemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConfigurationProperties(prefix = "kafka")
class KafkaProperties {
    lateinit var bootstrapServers: String
    lateinit var schemaRegistryUrl: String
    lateinit var producer: Producer
    lateinit var consumer: Consumer
    lateinit var topic: String

    class Producer {
        lateinit var keySerializer: String
        lateinit var valueSerializer: String
    }

    class Consumer {
        lateinit var groupId: String
        lateinit var keyDeserializer: String
        lateinit var valueDeserializer: String
        lateinit var autoOffsetReset: String
    }

    fun producerProperties(): Properties = Properties().apply {
        put("bootstrap.servers", bootstrapServers)
        put("schema.registry.url", schemaRegistryUrl)
        put("key.serializer", producer.keySerializer)
        put("value.serializer", producer.valueSerializer)
    }

    fun consumerProperties(): Properties = Properties().apply {
        put("bootstrap.servers", bootstrapServers)
        put("schema.registry.url", schemaRegistryUrl)
        put("group.id", consumer.groupId)
        put("key.deserializer", consumer.keyDeserializer)
        put("value.deserializer", consumer.valueDeserializer)
        put("auto.offset.reset", consumer.autoOffsetReset)
    }
}