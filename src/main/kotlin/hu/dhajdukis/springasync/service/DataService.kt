package hu.dhajdukis.springasync.service

import hu.dhajdukis.springasync.entity.Data
import hu.dhajdukis.springasync.entity.State
import hu.dhajdukis.springasync.repository.DataRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DataService(private val dataRepository: DataRepository, private val asyncService: AsyncService) {

    @Scheduled(cron = "\${scheduler.executor1-scheduler}")
    @Transactional
    fun collectDataInTodoState() {
        logger.info("--- collectDataInTodoState scheduled task start ---")
        val dataInTodoState = dataRepository.findByState(State.TODO)
        if (dataInTodoState == null) {
            logger.info("No data in TODO state")
            return
        }
        dataInTodoState.forEach {
            asyncService.asyncProcessor(it.id)
        }
        logger.info("--- collectDataInTodoState scheduled task stop ---")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(DataService::class.java)
    }
}
