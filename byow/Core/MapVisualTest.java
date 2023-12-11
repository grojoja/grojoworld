package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapVisualTest {
    private int width =  20;
    private int height = 20;
    public static ArrayList<Room2> generateRoom(TETile[][] world, int numRooms, long seed){
        Random r = new Random(seed);
        int maxWidth = 10;
        int maxLength = 6;
        ArrayList<Room2> list = new ArrayList<>();
        for(int i = 0; i < numRooms; i++){
            int length = r.nextInt(1,maxWidth)+2;
            int height = r.nextInt(1,maxLength)+2;
            int x = r.nextInt(40-length);
            int y = r.nextInt(40 - height);
            Position start = new Position(x,y);
            Room2.createRoom(world,start,length,height);
            Room2 room = new Room2(length,height,start);
            list.add(room);
        }
        return list;
    }
    public static void fillRooms(TETile[][] world, ArrayList<Room2> list){
        for(int i = 0; i < list.size(); i++){
            Room2.fillRoom(world, list.get(i).location,list.get(i).width,list.get(i).height);
        }
    }
    public static ArrayList<Hallway> hallwayList(TETile[][] world,ArrayList<Room2> list, long seed){
        ArrayList<Hallway> halls = new ArrayList<>();
        for(int i = 0; i < list.size()-1; i++){
            halls.add(Room2.Lhalls(world,list.get(i),list.get(i+1),seed));
        }
        return halls;
    }
    public static Position pos(Room2 one, long seed){
        Random r = new Random(seed);
        int xPosition = r.nextInt(one.width - 2) + one.location.getX() +1;
        int yPosition = r.nextInt(one.height - 2) + one.location.getY() +1;
        return new Position(xPosition,yPosition);
    }

    public static void fillHallsList(TETile[][] world, ArrayList<Hallway> halls){
        for(int i = 0; i < halls.size(); i++){
            Room2.fillHalls(world,halls.get(i));
        }
    }
    public static void main(String args[]){
        TERenderer ter = new TERenderer();
        ter.initialize(40, 40);
        long seed = 5;
        Random r = new Random(seed);
        TETile[][] world = new TETile[40][40];
        for(int i = 0; i < 40; i++){
            for(int j = 0; j < 40; j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
        int roomNums = 12;
        ArrayList<Room2> rooms = generateRoom(world,roomNums,seed);
        ArrayList<Hallway> halls = hallwayList(world,rooms,seed);
        fillHallsList(world,halls);
        fillRooms(world,rooms);
        ter.renderFrame(world);
    }
}
