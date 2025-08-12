package kr.co.kimga.fdsevaldemo.config

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.serialization.Serde
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaStreamConfig(
    @Value("\${spring.kafka.schema-registry-url}") private val schemaRegistryUrl: String
) {
    fun <T: SpecificRecord> specificAvroSerde(): Serde<T> {
        val serde = SpecificAvroSerde<T>()
        serde.configure(mapOf("schema.registry.url" to schemaRegistryUrl), false)
        return serde
    }
}