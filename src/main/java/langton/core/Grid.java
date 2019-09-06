package langton.core;

import java.util.HashSet;

/**
 * The class that holds the grid data for the algorithm.
 * It uses HashSet to hold the "black" cells only and it allows us to have an "infinite" grid.
 */
public class Grid {

    private HashSet<Vector2> cells;
    private Vector2 minArea;
    private Vector2 maxArea;

    public Grid(){
        reset();
    }

    public Vector2 getMinArea(){
        return minArea;
    }

    public Vector2 getMaxArea(){
        return maxArea;
    }

    public boolean isCellSet(Vector2 coordinates){
        return cells.contains(coordinates);
    }

    public void toggleCellAt(Vector2 coordinates){
        if(isCellSet(coordinates))
            cells.remove(coordinates);
        else{
            cells.add(new Vector2(coordinates)); // the new makes a copy and avoid having multiple Vector2 with the same coordinates.
            updateArea(coordinates);
        }
    }

    public void reset(){
        cells = new HashSet<>();
        maxArea = new Vector2();
        minArea = new Vector2();
    }

    private void updateArea(Vector2 coordinates){
        if(coordinates.x < minArea.x)
            minArea.x = coordinates.x;
        if(coordinates.y < minArea.y)
            minArea.y = coordinates.y;
        if(coordinates.x > maxArea.x)
            maxArea.x = coordinates.x;
        if(coordinates.y > maxArea.y)
            maxArea.y = coordinates.y;
    }
}
