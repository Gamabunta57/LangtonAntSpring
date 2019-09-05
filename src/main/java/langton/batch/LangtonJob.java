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

@EnableBatchProcessing
@Component
public class LangtonJob {

    @Autowired
    private JobBuilderFactory langtonJobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    private Job runLangtonAnt(JobCompletionNotificationListener listener, Step stepLangtonRun){
        return langtonJobBuilderFactory.get("runLangtonAnt")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(stepLangtonRun)
                .end()
                .build();
    }

    @Bean
    private Step stepLangtonRun(GridBatchWriter writer, LangtonIterationReader reader){
        return stepBuilderFactory.get("stepLangtonRun")
                .<Long, Grid>chunk(1)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    private LangtonAntProcessor processor(){
        return new LangtonAntProcessor();
    }

    @Bean
    private GridBatchWriter writer(){
        return new GridBatchWriter();
    }

    @Bean
    @StepScope
    private LangtonIterationReader reader(@Value("#{jobParameters['iteration']}")long iteration){
        LangtonIterationReader reader = new LangtonIterationReader();
        reader.setIteration(iteration);
        return reader;
    }
}
