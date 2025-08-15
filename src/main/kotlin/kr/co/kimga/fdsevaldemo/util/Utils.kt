package kr.co.kimga.fdsevaldemo.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val mapper = ObjectMapper().registerKotlinModule()

fun toContext(target: Any): Map<String, Any> {
    return mapper.convertValue(target)
}