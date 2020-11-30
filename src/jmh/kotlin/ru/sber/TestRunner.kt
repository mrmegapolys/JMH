package ru.sber

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode.All
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope.Thread
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit.NANOSECONDS

@State(Thread)
@BenchmarkMode(All)

@Warmup(iterations = 10)
@OutputTimeUnit(NANOSECONDS)
open class TestRunner {
    private lateinit var person: Person
    private lateinit var objectMapper: ObjectMapper
    private lateinit var personJson: String

    @Setup
    open fun setup() {
        person = Person(
            name = "Alex",
            surname = "Rudnev",
            age = 23,
            married = false
        )
        objectMapper = jacksonObjectMapper()
        personJson = objectMapper.writeValueAsString(person)
    }

    @Benchmark
    open fun testJsonSerialize(blackhole: Blackhole) {
        blackhole.consume(objectMapper.writeValueAsString(person))
    }

    @Benchmark
    open fun testJsonDeserialize(blackhole: Blackhole) {
        blackhole.consume(objectMapper.readValue<Person>(personJson))
    }
}