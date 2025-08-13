package kr.co.kimga.fdsevaldemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafkaStreams

@EnableKafkaStreams
@SpringBootApplication
class FdsevaldemoApplication

fun main(args: Array<String>) {
	runApplication<FdsevaldemoApplication>(*args)
}
