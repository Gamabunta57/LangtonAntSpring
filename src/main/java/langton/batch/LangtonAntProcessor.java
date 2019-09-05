package langton.batch;

import langton.core.Ant;
import langton.core.Grid;
import langton.core.Vector2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class LangtonAntProcessor  implements ItemProcessor<Long, Grid> {

    private static final Logger log = LoggerFactory.getLogger(LangtonAntProcessor.class);

    @Override
    public Grid process(Long iteration) throws Exception {
        Grid grid = new Grid();
        Ant ant = new Ant();

        grid.reset();
        ant.reset();

        log.info("Processing Langton algorithm for "+iteration.toString()+" iteration");

        while(iteration-- > 0)
            run1Step(grid, ant);

        return grid;
    }

    private void run1Step(Grid grid, Ant ant){
        Vector2 antCoordinates = ant.getCoordinates();

        if(grid.isCellSet(antCoordinates))
            ant.rotateLeft();
        else
            ant.rotateRight();

        ant.move();
        grid.toggleCellAt(antCoordinates);
    }
}
