package langton.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class LangtonIterationReader implements ItemReader<Long> {

    private Long iteration;
    private boolean hasGivenValue = false;

    @Override
    public Long read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
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
