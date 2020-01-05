package hu.dhajdukis.springasync.config

import hu.dhajdukis.springasync.entity.Data
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import java.lang.reflect.Method
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
@EnableAsync
class AsyncConfig(
        @Value("\${scheduler.executor1-pool-size}") val signPoolSize: String
) : AsyncConfigurer {

    @Bean(name = ["executor1"])
    override fun getAsyncExecutor(): Executor = Executors.newScheduledThreadPool(Integer.valueOf(signPoolSize))

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler? = AsyncExceptionHandler()
}

class AsyncExceptionHandler : AsyncUncaughtExceptionHandler {
    override fun handleUncaughtException(throwable: Throwable, method: Method, vararg params: Any?) {
        when (throwable) {
            is AsyncException -> {
                logger.error(
                        "${throwable.message}: ID: ${throwable.data.id} - Message: ${throwable.cause?.message}",
                        throwable.cause
                )
            }
            else -> throw throwable
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AsyncExceptionHandler::class.java)
    }
}

class AsyncException(val data: Data, message: String, cause: Throwable) : Throwable(message, cause)
