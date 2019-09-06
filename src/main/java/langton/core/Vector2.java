package langton.core;

/**
 * A simple 2D Vector class to hold coordinates data
 * The hashcode of that class is based on the held coordinates.
 * This allows us to use it in an hashset and defines its uniqueness based on coordinates.
 */
public class Vector2{
    public long x;
    public long y;

    public Vector2(){
        this(0,0);
    }

    public Vector2(long x, long y){
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 copy){
        this(copy.x,copy.y);
    }

    public void add(Vector2 other){
        x += other.x;
        y += other.y;
    }

    @Override
    public int hashCode(){
        return (x+","+y).hashCode();
    }

    @Override
    public String toString(){
        return "x: "+x+",y: "+y;
    }

    @Override
    public boolean equals(Object b){
        if(!(b instanceof Vector2))
            return false;
        return b.hashCode() == hashCode();
    }
}
