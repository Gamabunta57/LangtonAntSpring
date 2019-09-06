package langton;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * It is the Web entry point
 *
 * It waits for a call at "/run" with an parameter "iteration"
 * Then it runs a Job that apply the Langont's ant with "iteration" as a parameter.
 */
@RestController
public class LangtonRunController {

    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = "/run", method = RequestMethod.PUT)
    public Message runAnt(@RequestParam(value = "iteration") long iteration) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Job job = (Job) context.getBean("runLangtonAnt");

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addLong("iteration", iteration);

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        jobLauncher.run(job, jobParametersBuilder.toJobParameters());

        return new Message(true, "Success");
    }
}
