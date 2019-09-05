package langton.core;

import org.springframework.stereotype.Component;

@Component
public class Ant {

    private Vector2 position;
    private Vector2 direction;

    public Vector2 getCoordinates(){
        return position;
    }

    public void move(){
        position.add(direction);
    }

    public void rotateLeft(){
        Vector2 tmp = new Vector2(direction);
        direction.x = -tmp.y;
        direction.y = tmp.x;
    }

    public void rotateRight(){
        Vector2 tmp = new Vector2(direction);
        direction.x = tmp.y;
        direction.y = -tmp.x;
    }

    public void reset(){
        position = new Vector2();
        direction = new Vector2(1,0);
    }
}
