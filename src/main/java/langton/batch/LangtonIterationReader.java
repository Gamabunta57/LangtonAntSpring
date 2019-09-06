package langton.batch;

import org.springframework.batch.item.ItemReader;

/**
 * This class has been made to hold the number of iteration and give it back to a job.
 * IMO, it's a "huge" thing just to get 1 data.
 */
public class LangtonIterationReader implements ItemReader<Long> {

    private Long iteration;
    private boolean hasGivenValue = false;

    @Override
    public Long read(){
        if(hasGivenValue)
            return null;

        hasGivenValue = true;
        return getIteration();
    }

    public void setIteration(final Long iteration) {
        this.iteration = iteration;
    }

    public Long getIteration(){
        return iteration;
    }
}
