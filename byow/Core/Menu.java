package byow.Core;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.util.Map;
import java.util.Random;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
public class Menu {
    private static TERenderer ter = new TERenderer();
    private static final int WIDTH = 60;
    private static char choice;
    private static final int HEIGHT = 60;
    private static long SEED;
    private static boolean start;
    private static final int MENUWIDTH = 40;
    private static String megaString = "";
    private static final int MENUHEIGHT = 40;
    private static int score;
    private static World temp;
    private static int health = 10;
    private static int secondHealth = 10;
    private static int flowerCount;
    private static boolean gameOver;
    private static boolean revival = true;
    private static int revivalCount = 1;
    private static int round = 1;
    public Menu() {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        StdDraw.setCanvasSize(MENUWIDTH*16, MENUHEIGHT*16);
        StdDraw.setXscale(0, MENUWIDTH);
        StdDraw.setYscale(0, MENUHEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.enableDoubleBuffering();
    }
    private static void itemsMouse(World map){
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if(map.world()[x][y] == Tileset.WALL){
            ter.renderFrame(map.world());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(17, MENUHEIGHT, "Wall");
        }
        if(map.world()[x][y] == Tileset.FLOOR){
            ter.renderFrame(map.world());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(17, MENUHEIGHT, "Floor");
        }
        if(map.world()[x][y] == Tileset.AVATAR){
            ter.renderFrame(map.world());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(17, MENUHEIGHT, "Player");
        }
        if(map.world()[x][y] == Tileset.FLOWER){
            ter.renderFrame(map.world());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(17, MENUHEIGHT, "Flower");
        }
        if(map.world()[x][y] == Tileset.SAND){
            ter.renderFrame(map.world());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(17, MENUHEIGHT, "Sand");
        }
        if(map.world()[x][y] == Tileset.NOTHING){
            ter.renderFrame(map.world());
            StdDraw.enableDoubleBuffering();
            StdDraw.setPenColor(Color.white);
            StdDraw.text(17, MENUHEIGHT, "Nothing");
        }
        StdDraw.setPenColor(Color.white);
        StdDraw.text(MENUWIDTH - 15, MENUHEIGHT, "Mouse X: " + String.valueOf(StdDraw.mouseX()));
        StdDraw.text(MENUWIDTH - 5, MENUHEIGHT,"Mouse Y: " + String.valueOf(StdDraw.mouseY()));
        StdDraw.setPenColor(Color.white);
        StdDraw.text(5, MENUHEIGHT + 2, "Player 1 Health: " + String.valueOf(health));
        StdDraw.text(5, MENUHEIGHT+1, "Player 2 Health : " + String.valueOf(secondHealth));
        StdDraw.text(6, MENUHEIGHT, "Remaining Flowers: " + String.valueOf(flowerCount));
    }
    private static void playerLocation(World map){
        int x = map.getPlayer().getX();
        int y = map.getPlayer().getY();
        if(map.world()[x][y] == Tileset.WALL){
            StdDraw.text(MENUWIDTH - 10, MENUHEIGHT, "Wall");
        }
        if(map.world()[x][y] == Tileset.FLOOR){
            StdDraw.text(MENUWIDTH - 10, MENUHEIGHT, "Floor");
        }
        if(map.world()[x][y] == Tileset.AVATAR){
            StdDraw.text(MENUWIDTH - 10, MENUHEIGHT, "Player");
        }
    }

    private static void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        int midWidth = MENUWIDTH / 2;
        int midHeight = MENUHEIGHT / 2;
        StdDraw.clear(Color.black);
        Font bigFont = new Font("Roboto", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, MENUHEIGHT - 5, s);
        StdDraw.show();
    }
    private static void showMenu() {
        // Draw the GUI
        Font title = new Font("Times New Roman", Font.BOLD, 25);
        Font mainMenu = new Font("Roboto Mono", Font.BOLD, 16);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(MENUWIDTH/2,MENUHEIGHT-8, "ESCAPE FROM JOJO: CHUNKY ISLAND");
        StdDraw.setFont(mainMenu);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.picture(MENUWIDTH/2,MENUHEIGHT/2 - 5,"/Users/grojoja/Desktop/fa22-proj3-g67/proj3/byow/Core/images/magicut_1670125000937.png");
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT / 4, "New Game (N/n)");
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT / 5, "Load Game (L / l)");
        StdDraw.text(MENUWIDTH / 2, MENUHEIGHT / 6, "Quit (Q / q)");
        StdDraw.show();
    }
    private static World options() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(1);
        }
        while (StdDraw.hasNextKeyTyped()) {
            do {
                if (!StdDraw.hasNextKeyTyped()) {
                    continue;
                }
                choice = StdDraw.nextKeyTyped();
                if(choice == 'l' || choice == 'L' || choice == 'n' || choice == 'N' || choice == 'Q' || choice == 'q'){
                    break;
                }
            }
            while(choice != 'l' && choice != 'L' && choice != 'n' && choice != 'N' && choice != 'Q' && choice != 'q');
            megaString += choice;
            char word = 'l';
            if (choice == 'N' || choice == 'n') {
                drawFrame("Please enter a seed: ");
                StdDraw.pause(1000);
                String seed = "";
                do{
                    if(!StdDraw.hasNextKeyTyped()){
                        continue;
                    }
                    word = StdDraw.nextKeyTyped();
                    if(word == 's'){
                        break;
                    }
                    seed += word;
                    megaString += word;
                    StdDraw.clear(Color.black);
                    StdDraw.text(MENUWIDTH/2, MENUHEIGHT /2, "Press s to start");
                    StdDraw.text(MENUWIDTH/2, MENUHEIGHT /3, "Your seed is: " + seed);
                    StdDraw.show();
                }
                while(word != 's');
                StdDraw.pause(300);
                long actualSeed = Long.valueOf(seed);
                MapGenerator world = new MapGenerator(actualSeed);
                return world.generateWorld();
            }
            if (choice == 'Q' || choice == 'q') {
                StdDraw.clear(Color.black);
                StdDraw.text(WIDTH / 2, HEIGHT / 2, "Thanks for playing!");
                StdDraw.show();
                StdDraw.pause(500);
                System.exit(0);
            }
            if(choice == 'L' || choice == 'l'){
                return loadMap();
            }
        }
        return null;
    }
    private static World movement(World world,char move){
        if(move == 'w' || move == 'W'){
            if(world.world()[world.getPlayer().getX()][world.getPlayer().getY()+1] != Tileset.WALL) {
                if(world.world()[world.getPlayer().getX()][world.getPlayer().getY()+1] == Tileset.SAND) {
                    health--;
                }
                world.world()[world.getPlayer().getX()][world.getPlayer().getY()+1] = Tileset.AVATAR;
                world.world()[world.getPlayer().getX()][world.getPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getPlayer().getX(), world.getPlayer().getY()+1);
                if(world.world()[world.getPlayer().getX()][world.getPlayer().getY()+1] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(health < 10) {
                        health++;
                    }
                }
                ter.renderFrame(world.world());
                return new World(player, world.world(),world.getSecondPlayer());
            }
        }
        if(move == 's' || move == 'S'){
            if(world.world()[world.getPlayer().getX()][world.getPlayer().getY()-1] != Tileset.WALL) {
                if(world.world()[world.getPlayer().getX()][world.getPlayer().getY()-1] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(health < 10) {
                        health++;
                    }
                }
                if(world.world()[world.getPlayer().getX()][world.getPlayer().getY()-1] == Tileset.SAND) {
                    health--;
                }
                world.world()[world.getPlayer().getX()][world.getPlayer().getY()-1] = Tileset.AVATAR;
                world.world()[world.getPlayer().getX()][world.getPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getPlayer().getX(), world.getPlayer().getY()-1);
                return new World(player, world.world(),world.getSecondPlayer());
            }
        }
        if(move == 'a' || move == 'A'){
            if(world.world()[world.getPlayer().getX()-1][world.getPlayer().getY()] != Tileset.WALL) {
                if(world.world()[world.getPlayer().getX()-1][world.getPlayer().getY()] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(health < 10) {
                        health++;
                    }
                }
                if(world.world()[world.getPlayer().getX()-1][world.getPlayer().getY()] == Tileset.SAND) {
                    health--;
                }
                world.world()[world.getPlayer().getX()-1][world.getPlayer().getY()] = Tileset.AVATAR;
                world.world()[world.getPlayer().getX()][world.getPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getPlayer().getX()-1, world.getPlayer().getY());
                return new World(player, world.world(),world.getSecondPlayer());
            }
        }
        if(move == 'd' || move == 'D'){
            if(world.world()[world.getPlayer().getX()+1][world.getPlayer().getY()] != Tileset.WALL) {
                if(world.world()[world.getPlayer().getX()+1][world.getPlayer().getY()] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(health < 10) {
                        health++;
                    }
                }
                if(world.world()[world.getPlayer().getX()+1][world.getPlayer().getY()] == Tileset.SAND) {
                    health--;
                }
                world.world()[world.getPlayer().getX()+1][world.getPlayer().getY()] = Tileset.AVATAR;
                world.world()[world.getPlayer().getX()][world.getPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getPlayer().getX()+1, world.getPlayer().getY());
                return new World(player, world.world(),world.getSecondPlayer());
            }
        }
        return new World(world.getPlayer(),world.world(),world.getSecondPlayer());
    }
    private static int scanFlowers(World world){
        int count = 0;
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                if(world.world()[i][j] == Tileset.FLOWER){
                    count++;
                }
            }
        }
        return count;
    }
    private static World secondMovement(World world,char move){
        if(move == 'i' || move == 'I'){
            if(world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()+1] != Tileset.WALL) {
                if(world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()-1] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(secondHealth < 10) {
                        secondHealth++;
                    }
                }
                if(world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()-1] == Tileset.SAND) {
                    secondHealth--;
                }
                world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()+1] = Tileset.AVATAR;
                world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getSecondPlayer().getX(), world.getSecondPlayer().getY()+1);
                return new World(world.getPlayer(), world.world(),player);
            }
        }
        if(move == 'k' || move == 'K'){
            if(world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()-1] != Tileset.WALL) {
                if(world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()-1] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(secondHealth < 10) {
                        secondHealth++;
                    }
                }
                if(world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()-1] == Tileset.SAND) {
                    secondHealth--;
                }
                world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()-1] = Tileset.AVATAR;
                world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getSecondPlayer().getX(), world.getSecondPlayer().getY()-1);
                return new World(world.getPlayer(), world.world(),player);
            }
        }
        if(move == 'j' || move == 'J'){
            if(world.world()[world.getSecondPlayer().getX()-1][world.getSecondPlayer().getY()] != Tileset.WALL) {
                if(world.world()[world.getSecondPlayer().getX()-1][world.getSecondPlayer().getY()] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(secondHealth < 10) {
                        secondHealth++;
                    }
                }
                if(world.world()[world.getSecondPlayer().getX()-1][world.getSecondPlayer().getY()] == Tileset.SAND) {
                    secondHealth--;
                }
                world.world()[world.getSecondPlayer().getX() - 1][world.getSecondPlayer().getY()] = Tileset.AVATAR;
                world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getSecondPlayer().getX() - 1, world.getSecondPlayer().getY());
                return new World(world.getPlayer(), world.world(),player);
            }
        }
        if(move == 'l' || move == 'L'){
            if(world.world()[world.getSecondPlayer().getX()+1][world.getSecondPlayer().getY()] != Tileset.WALL) {
                if(world.world()[world.getSecondPlayer().getX()+1][world.getSecondPlayer().getY()] == Tileset.FLOWER) {
                    score++;
                    flowerCount--;
                    if(secondHealth < 10) {
                        secondHealth++;
                    }
                }
                if(world.world()[world.getSecondPlayer().getX()+1][world.getSecondPlayer().getY()] == Tileset.SAND) {
                   secondHealth--;
                }
                world.world()[world.getSecondPlayer().getX() + 1][world.getSecondPlayer().getY()] = Tileset.AVATAR;
                world.world()[world.getSecondPlayer().getX()][world.getSecondPlayer().getY()] = Tileset.FLOOR;
                Position player = new Position(world.getSecondPlayer().getX() + 1, world.getSecondPlayer().getY());
                return new World(world.getPlayer(), world.world(),player);
            }
        }
        return new World(world.getPlayer(),world.world(),world.getSecondPlayer());
    }
    public static void main(String args[]){
        Menu menu = new Menu();
        showMenu();
        while(revival == true) {
            playGame(options());
            if (gameOver == true && (health == 5 && secondHealth == 5)) {
                gameOver = false;
                revivalCount--;
                playRevival(MapGenerator.generateTrapWorld());
            }
            playGame(temp);
        }
        StdDraw.picture(MENUWIDTH - 5, HEIGHT/2, "/Users/grojoja/Desktop/fa22-proj3-g67/proj3/byow/Core/images/magicut_1670123961943 (1).png");
        StdDraw.show();
        StdDraw.pause(500);
    }
    private static void save(String string) {
        try {
            FileWriter file = new FileWriter("savedProgress.txt");
            BufferedWriter buffered = new BufferedWriter(file);
            buffered.write(string);
            buffered.close();
        } catch (IOException exception) {
            System.out.println("Error when trying to run file");
        }
    }

    private static String load() {
        String string;
        try {
            FileReader file = new FileReader("savedProgress.txt");
            BufferedReader buffered = new BufferedReader(file);
            StringBuffer x = new StringBuffer();
            while ((string = buffered.readLine()) != null) {
                x.append(string);
            }
            buffered.close();
            return x.toString();
        } catch (IOException exception) {
            return null;
        }
    }
    private static void playGame(World map){
        char move;
        String string = "";
        while(!gameOver){
            flowerCount = scanFlowers(map);
            itemsMouse(map);
            StdDraw.show();
            if(!StdDraw.hasNextKeyTyped()){
                continue;
            }
            move = StdDraw.nextKeyTyped();
            megaString += move;
            string += move;
            if(flowerCount == 0){
                round++;
                if(revivalCount < 1){
                    revivalCount++;
                }
                MapGenerator world = new MapGenerator(round);
                playGame(world.generateWorld());
            }
            if(health == 0 || secondHealth == 0){
                gameOver = true;
                flowerCount = 5;
                if(revivalCount == 0) {
                    revival = false;
                    drawFrame("You Lose.");
                    StdDraw.pause(1000);
                }
                else{
                    temp = map;
                    health = 5;
                    secondHealth = 5;
                }
            }
            if(move == 'p'){
                String [] decision = megaString.split("p");
                save(decision[0]);
                System.exit(0);
            }
            map = movement(map,move);
            map = secondMovement(map,move);
        }
    }
    private static void playRevival(World map){
        char move;
        String string = "";
        while(!gameOver){
            flowerCount = scanFlowers(map);
            itemsMouse(map);
            StdDraw.show();
            if(!StdDraw.hasNextKeyTyped()){
                continue;
            }
            move = StdDraw.nextKeyTyped();
            string += move;
            if(flowerCount == 0){
                break;
            }
            map = movement(map,move);
            map = secondMovement(map,move);
        }
    }
    private static World loadMap(){
        String s = load();
        int length = s.length();
        String seed = "";
        World map = null;
        char moves;
        int i = 1;
        while(i < length -1) {
            while (Character.isDigit(s.charAt(i))) {
                seed += s.charAt(i);
                if(i == length - 1){
                    break;
                }
                else{
                    i++;
                }
            }
            MapGenerator world = new MapGenerator(Long.valueOf(seed));
            map = world.generateWorld();
            while (!Character.isDigit(s.charAt(i))) {
                moves = s.charAt(i);
                map = movement(map, moves);
                map = secondMovement(map,moves);
                if(i == length - 1){
                    break;
                }
                else{
                    i++;
                }
                ter.renderFrame(map.world());
            }
            return map;
        }
        return map;
    }
}

