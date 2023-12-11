package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import static byow.Core.MapVisualTest.pos;

public class Room2 {
    //    protected int xPos;
//    protected int yPos;
    protected int width;
    protected int height;
    protected Position location;
    protected static final int MINIMUM_LENGTH = 5;
    protected static final int MAXIMUM_LENGTH = 10;


    // Constructs a new room given a random seed
    public Room2(int width, int height, Position location) {
        this.location = location;
        this.height = height;
        this.width = width;
    }
    public static void createRoom(TETile[][] world, Position x, int width, int height) {
        for (int i = 0; i < width; i++) {
            world[x.getX() + i][x.getY()] = Tileset.WALL;
            world[x.getX() + i][x.getY() + height - 1] = Tileset.WALL;
        }
        for (int j = 0; j < height; j++) {
            world[x.getX()][x.getY() + j] = Tileset.WALL;
            world[x.getX() + width - 1][x.getY() + j] = Tileset.WALL;
        }
    }
    // can probably use a Room variable for this because I am not sure if the height and width
    //will stay constant so we would want to take the room we just created to access the data
    public static void fillRoom(TETile[][] world, Position x, int width, int height) {
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                world[x.getX() + i][x.getY() + j] = Tileset.FLOOR;
            }
        }
    }
    public static void addCorners(TETile[][] world, Position corner){
        for(int i = corner.getX()-1; i <= corner.getX() + 1; i++){
            for(int j = corner.getY() -1; j < corner.getY()+2; j++){
                world[i][j] = Tileset.WALL;
            }
        }
        world[corner.getX()][corner.getY()] = Tileset.FLOOR;
    }
    public static Hallway Lhalls(TETile[][] world, Room2 one, Room2 two, long seed){
        Position p1 = pos(one,seed);
        Position p2 = pos(two,seed);
        Position horizontalStart = Hallway.smallerX(p1, p2);
        Position horCornerPt = Hallway.addHorizontalHallway(world, horizontalStart, Math.abs(p2.getX() - p1.getX()));
        Position verticalStart = Hallway.smallerY(horCornerPt, Hallway.biggerX(p1, p2));
        Hallway.addVerticalHallway(world, verticalStart, Math.abs(p2.getY() - p1.getY()));
        addCorners(world, horCornerPt);
        return new Hallway(horCornerPt, horizontalStart, Hallway.biggerX(p1, p2), 0);
    }
    public static void fillHalls(TETile[][] world, Hallway hall){
        Hallway.addHorizontalFloor(world,hall.start,Math.abs(hall.startCorner.getX() - hall.start.getX() + 1));
        Hallway.addVerticalFloor(world,Hallway.smallerY(hall.startCorner,hall.end),Math.abs(hall.startCorner.getY() - hall.end.getY()));
    }
}
