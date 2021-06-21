package by.masarnovsky.releasedemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReleaseDemonApplication

fun main(args: Array<String>) {
	runApplication<ReleaseDemonApplication>(*args)
}
