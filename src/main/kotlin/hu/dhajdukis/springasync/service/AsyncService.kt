package hu.dhajdukis.springasync.service

import hu.dhajdukis.springasync.config.AsyncException
import hu.dhajdukis.springasync.entity.State
import hu.dhajdukis.springasync.repository.DataRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class AsyncService(private val dataRepository: DataRepository) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async("executor1")
    fun asyncProcessor(id: Long) {
        val data = dataRepository.findOneById(id)
        data.checkCounter()
        try {
            logger.info("Changing data state. Id: $id")
            data.changeState(State.DONE)
        } catch (ex: Exception) {
            data.increaseCounter()
            throw AsyncException(data, "Change data state error", ex)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AsyncService::class.java)
    }
}


