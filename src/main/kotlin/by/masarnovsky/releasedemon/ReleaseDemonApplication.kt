package by.masarnovsky.releasedemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("classpath:tokens.properties", "classpath:telegram.properties")
class ReleaseDemonApplication

fun main(args: Array<String>) {
	runApplication<ReleaseDemonApplication>(*args)
}
