package langton.batch;

import langton.core.Grid;
import langton.core.GridWriter;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class GridBatchWriter implements ItemWriter<Grid> {

    @Override
    public void write(List items) throws Exception {
        GridWriter writer = new GridWriter();
        writer.export((Grid) items.get(0));
    }
}
