package hu.dhajdukis.springasync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SpringAsyncApplication

fun main(args: Array<String>) {
    runApplication<SpringAsyncApplication>(*args)
}
