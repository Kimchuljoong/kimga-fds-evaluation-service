package kr.co.kimga.fdsevaldemo.intg

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import kr.co.kimga.fdsevaldemo.model.TransactionEvent
import kr.co.kimga.fdsevaldemo.model.TransactionEventType
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import java.time.Duration
import java.time.Instant

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = ["transactions", "fds-alerts"])
class PipelineTest(
    @Autowired private val kafkaTemplate: KafkaTemplate<String, TransactionEvent>,
    @Autowired private val kafkaConsumerFactory: ConsumerFactory<String, TransactionEvent>
) {

    @Test
    @DisplayName("트랜잭션 이벤트가 FDS에 의해 탐지되면 알림 토픽으로 메시지를 전송한다")
    fun `send message to alert topic when transaction event will be detected by FDS`() {

        // given
        val transactionEvent = makeTransactionEvent()

        // when
        kafkaTemplate.send("transactions", transactionEvent)

        // then
        val consumer = kafkaConsumerFactory.createConsumer()
        consumer.subscribe(listOf("fds-alerts"))

        val record: ConsumerRecord<String, TransactionEvent> =
            KafkaTestUtils.getSingleRecord(consumer, "fds-alerts", Duration.ofSeconds(3))

        assertEquals(transactionEvent.transactionId, record.value().transactionId)

        consumer.close()

    }

    private fun makeTransactionEvent() = TransactionEvent(
        "tx0000001",
        "account0001",
        TransactionEventType.BUY,
        1L,
        10_000L,
        Instant.now(),
    )
}