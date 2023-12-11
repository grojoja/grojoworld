package byow.Core;
import byow.TileEngine.Tileset;
import byow.Core.Position;
import byow.TileEngine.TETile;


public class Hallway {
    public Position start;
    public Position end;
    public int length;
    public Position startCorner;
    public Hallway(Position startCorner,Position start, Position end, int length){
        this.startCorner = startCorner;
        this.start = start;
        this.end = end;
        this.length = length;
    }
    public static Position addHorizontalHallway(TETile[][] world, Position x, int width){
        for(int i = 0; i < width; i++){
                world[x.getX() + i][x.getY() - 1] = Tileset.WALL;
                world[x.getX() + i][x.getY() + 1] = Tileset.WALL;
        }
        //returning the end position
        return new Position(x.getX()+width,x.getY());
    }
    public static void addVerticalFloor(TETile world[][], Position x, int height){
        for(int i = 0; i < height; i++){
            world[x.getX()][x.getY()+i] = Tileset.FLOOR;
        }
    }
    public static void addHorizontalFloor(TETile world[][], Position x, int width){
        for(int i = 0; i < width; i++){
            world[x.getX()+i][x.getY()] = Tileset.FLOOR;
        }
    }
    public static Position addVerticalHallway(TETile world[][], Position x, int width){
        for(int i = 0; i < width; i++){
            world[x.getX() - 1][x.getY() + i] = Tileset.WALL;
            world[x.getX() + 1][x.getY() + i] = Tileset.WALL;
        }
        return new Position(x.getX(),x.getY()+width);
    }
    public static Position smallerX(Position one, Position two){
        if(one.getX() > two.getX()){
            return two;
        }
        return one;
    }
    public static Position smallerY(Position one, Position two){
        if(one.getY() > two.getY()){
            return two;
        }
        return one;
    }
    public static Position biggerX(Position one, Position two){
        if(one.getX() > two.getX()){
            return one;
        }
        return two;
    }
}