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
import org.openjdk.jmh.infra.Blackhole
import ru.sber.PersonOuterClass.Person.newBuilder
import ru.sber.PersonOuterClass.Person.parseFrom
import java.util.concurrent.TimeUnit.NANOSECONDS

@State(Thread)
@BenchmarkMode(All)
@OutputTimeUnit(NANOSECONDS)
open class TestRunner {
    private lateinit var person: Person
    private lateinit var personJson: String
    private lateinit var personProto: PersonOuterClass.Person
    private lateinit var personProtoBytes: ByteArray
    private lateinit var objectMapper: ObjectMapper

    private val name = "Alex"
    private val surname = "Rudnev"
    private val age = 23
    private val married = false

    @Setup
    open fun setup() {
        objectMapper = jacksonObjectMapper()

        person = Person(
            name = name,
            surname = surname,
            age = age,
            married = married
        )
        personProto = newBuilder()
            .apply {
                name = name
                surname = surname
                age = age
                married = married
            }
            .build()

        personProtoBytes = personProto.toByteArray()
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

    @Benchmark
    open fun testProtoSerialize(blackhole: Blackhole) {
        blackhole.consume(personProto.toByteArray())
    }

    @Benchmark
    open fun testProtoDeserialize(blackhole: Blackhole) {
        blackhole.consume(parseFrom(personProtoBytes))
    }
}