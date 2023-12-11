package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MapGenerator {
    private static int WIDTH =  60;
    private static int HEIGHT = 60;
    private static int MAX = 30;
    private static long seed;
    public MapGenerator(long seed){
        this.seed = seed;
    }
    public static ArrayList<Room2> generateRoom(TETile[][] world, int numRooms){
        Random r = new Random(seed);
        int maxWidth = 10;
        int maxLength = 6;
        ArrayList<Room2> list = new ArrayList<>();
        for(int i = 0; i < numRooms; i++){
            int length = r.nextInt(2,maxWidth)+2;
            int height = r.nextInt(2,maxLength)+2;
            int x = r.nextInt(40-length);
            int y = r.nextInt(40 - height);
            Position start = new Position(x,y);
            Room2.createRoom(world,start,length,height);
            Room2 room = new Room2(length,height,start);
            list.add(room);
        }
        return list;
    }
    public static void fillObj(TETile[][] world,int num){
        Random rand = new Random(seed);
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                int len = rand.nextInt(HEIGHT);
                int wide = rand.nextInt(WIDTH);
                if(world[len][wide] == Tileset.FLOOR){
                    world[len][wide] = Tileset.FLOWER;
                }
            }
        }
    }
    public static void fillDamage(TETile[][] world,int num){
        Random rand = new Random(seed);
        for(int i = 0; i < num; i++){
            for(int j = 0; j < num; j++){
                int len = rand.nextInt(HEIGHT);
                int wide = rand.nextInt(WIDTH);
                if(world[len][wide] == Tileset.FLOOR){
                    world[len][wide] = Tileset.SAND;
                }
            }
        }
    }
    public static void fillRooms(TETile[][] world, ArrayList<Room2> list){
        for(int i = 0; i < list.size(); i++){
            Room2.fillRoom(world, list.get(i).location,list.get(i).width,list.get(i).height);
        }
    }
    public static ArrayList<Hallway> hallwayList(TETile[][] world,ArrayList<Room2> list){
        ArrayList<Hallway> halls = new ArrayList<>();
        for(int i = 0; i < list.size()-1; i++){
            halls.add(Room2.Lhalls(world,list.get(i),list.get(i+1),seed));
        }
        return halls;
    }
    public static Position pos(Room2 one){
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
    public static Position playerPosition(TETile[][] world, ArrayList<Room2> rooms){
        Random r = new Random(seed);
        Position room = rooms.get(r.nextInt(rooms.size())).location;
        Position player = new Position(room.getX() + 1, room.getY() + 1);
        world[room.getX()+1][room.getY()+1] = Tileset.AVATAR;
        return player;
    }
    public static Position secondPlayerPosition(TETile[][] world, ArrayList<Room2> rooms){
        Random r = new Random(seed);
        Position room = rooms.get(r.nextInt(rooms.size())).location;
        Position player = new Position(room.getX() + 2, room.getY() + 1);
        world[room.getX()+2][room.getY()+1] = Tileset.AVATAR;
        return player;
    }

    public static World generateWorld(){
        MapGenerator map = new MapGenerator(seed);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        Random r = new Random(seed);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
        int roomNums = r.nextInt(MAX) + 5;
        ArrayList<Room2> rooms = map.generateRoom(world,roomNums);
        ArrayList<Hallway> halls = map.hallwayList(world,rooms);
        map.fillHallsList(world,halls);
        map.fillRooms(world,rooms);
        fillObj(world,20);
        fillDamage(world,30);
        Position one = playerPosition(world,rooms);
        Position second = secondPlayerPosition(world,rooms);
        ter.renderFrame(world);
        return new World(one,world,second);
    }
    public static World generateTrapWorld(){
        MapGenerator map = new MapGenerator(seed + 26);
        TERenderer ter = new TERenderer();
        ter.initialize(50,50);
        Random r = new Random(seed + 26);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                world[i][j] = Tileset.NOTHING;
            }
        }
        ArrayList<Room2> rooms = map.generateRoom(world,3);
        map.fillRooms(world,rooms);
        ArrayList<Hallway> halls = map.hallwayList(world,rooms);
        map.fillHallsList(world,halls);
        map.fillRooms(world,rooms);
        fillObj(world,40);
        Position one = playerPosition(world,rooms);
        Position second = secondPlayerPosition(world,rooms);
        ter.renderFrame(world);
        return new World(one,world,second);

    }
}
