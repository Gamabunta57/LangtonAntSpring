package langton.batch;

import langton.core.Grid;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

/**
 * This is the main job that is run from the @RestController (although it could be run from another entry point).
 * It is marked as :
 *  - @Component to be able to find the "runLangtonAnt" job and run it.
 *  - @EnableBatchProcessing to access "JobBuilderFactory" and "StepBuilderFactory" (without it, Spring Crash because it can't find those at startup)
 *
 *  It is heavily inspired by the Spring Boot tutorials.
 */
@Component
@EnableBatchProcessing
public class LangtonJob {

    @Autowired
    private JobBuilderFactory langtonJobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * Builds a job "runLangtonAnt" that will run the Langton's ant algorithm.
     * The @Bean annotation is useful to not have to instantiate the listener and stepLangtonRun
     *
     * @param listener during the progression of the job, listener is called multiple times at some point of the run.
     * @param stepLangtonRun defines the flow to run
     * @return the built Job
     */
    @Bean
    private Job runLangtonAnt(JobCompletionNotificationListener listener, Step stepLangtonRun){
        return langtonJobBuilderFactory.get("runLangtonAnt")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepLangtonRun)
                .end()
                .build();
    }

    /**
     * This build a step that run the entire algorithm.
     * Here it is marked as @Bean to benefit from the IoC container.
     * It avoid us to care about the reader instantiation.
     * This is one use a @Value to get a value from a job parameter and by this way, this is easier to define a reader and get the job parameter information.
     *
     * @param reader the reader that is used to get the "iteration" data information
     * @return a step that hold the entire Langton's ant processing.
     */
    @Bean
    private Step stepLangtonRun(LangtonIterationReader reader){
        return stepBuilderFactory.get("stepLangtonRun")
                .<Long, Grid>chunk(1)
                .reader(reader)
                .processor(processor())
                .writer(writer())
                .build();
    }

    private LangtonAntProcessor processor(){
        return new LangtonAntProcessor();
    }

    private GridBatchWriter writer(){
        return new GridBatchWriter();
    }

    /**
     * This method gives us back a LangtonIterationReader.
     * I would have prefer another way to accomplish that because, here it is just a trick to pass the parameter
     * "iteration" (that we get from the HTTP request) to the job.
     * It use the @StepScope annotation to access @Value annotation.
     * @Bean is defined for being able to benefits from the IoC container.
     * So we don't have to find the "iteration" number and stepLangtonRun is also easier to call thanks to it.
     *
     * @param iteration the number of iteration to run the algorithm. It is marked with @Value to automatically set this value with a job parameter.
     * @return a reader that holds the iteration number.
     */
    @Bean
    @StepScope
    private LangtonIterationReader reader(@Value("#{jobParameters['iteration']}")long iteration){
        LangtonIterationReader reader = new LangtonIterationReader();
        reader.setIteration(iteration);
        return reader;
    }
}
