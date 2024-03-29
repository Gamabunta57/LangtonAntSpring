package langton.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * This listener is based on Spring's Boot tutorials.
 * They used one so do I. But indeed, in this state, it's almost useless.
 *
 * It is mark as @Component for the IoC container and automatically use it in LangtonJob class.
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void afterJob(JobExecution jobExecution){
        if(jobExecution.getStatus() != BatchStatus.COMPLETED)
            return;

        logger.info("Langton's ant job finished");
    }
}
