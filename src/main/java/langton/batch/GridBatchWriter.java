package langton.batch;

import langton.core.Grid;
import langton.core.GridWriter;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Just a wrapper for the GridWriter
 * It exists only because I wanted to keep the first version of the GridWriter pristine.
 * Indeed, I may thing about removing it and make GridWriter evolve to use it instead.
 */
public class GridBatchWriter implements ItemWriter<Grid> {

    @Override
    public void write(List items) throws Exception {
        GridWriter writer = new GridWriter();
        writer.export((Grid) items.get(0));
    }
}
