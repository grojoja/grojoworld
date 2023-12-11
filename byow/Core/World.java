package byow.Core;

import byow.TileEngine.TETile;

public class World {
    private Position player;
    private TETile[][] world;
    private Position secondPlayer;
    public World(Position player,TETile[][] world,Position secondPlayer){
        this.player = player;
        this.world = world;
        this.secondPlayer = secondPlayer;
    }
    public Position getSecondPlayer(){
        return this.secondPlayer;
    }
    public Position getPlayer(){
        return this.player;
    }
    public TETile[][] world(){
        return this.world;
    }
}
