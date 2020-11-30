package ru.sber

import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder

fun main() {
    Runner(
        OptionsBuilder()
            .include(TestRunner::javaClass.name)
            .forks(1)
            .build()
    ).run()
}