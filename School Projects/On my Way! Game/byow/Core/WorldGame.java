
package byow.Core;


import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class WorldGame {

    Random r;
    long seed;
    String action;
    private final TETile[][] world;
    private final int width;
    private final int height;
    public Point origin;


    WorldGame(String i, int w, int h) {
        String[] input = i.split("");
        action = input[0];
        width = w;
        height = h;
        world = new TETile[w][h];
        seed = Long.parseLong(input[1]);
        int index = 2;
        while (index < input.length && !input[index].equals("S")) {
            seed *= 10;
            seed += Long.parseLong(input[index]);
            index++;
        }
        r = new Random(seed);
        origin = new Point(r.nextInt(width), r.nextInt(height));
    }

    Space map;

    private void createSpace(Space currRoom) {
        int hor = Math.max(r.nextInt(width / 4), 4);
        int ver = Math.max(r.nextInt(height / 4), 4);
        if (currRoom == null) {
            currRoom = new Space(origin, hor, ver);
            validateSpace(currRoom);
        }
        if (currRoom.upRoom == null && currRoom.center.y < height - 1) { //Create an upSpace
            currRoom.upRoom = new Space();
            currRoom.upRoom.center = new Point(Math.min(currRoom.center.x, width -1), Math.min(currRoom.upPoint.y + (ver / 2) + 1, height - 1));
            currRoom.upRoom.upPoint = new Point(currRoom.center.x, Math.min(currRoom.upRoom.center.y + (ver / 2), height - 1));
            currRoom.upRoom.downPoint = new Point(currRoom.center.x, Math.max(currRoom.upRoom.center.y - (ver / 2), 0));
            if (flipCoin()) { //create a Room
                currRoom.upRoom.rightPoint =  new Point(Math.min(currRoom.center.x + (hor / 2), width - 1), currRoom.upRoom.center.y);
                currRoom.upRoom.leftPoint =  new Point(Math.max(currRoom.center.x - (hor / 2), 0), currRoom.upRoom.center.y);
            } else { // create a Hallway
                currRoom.upRoom.rightPoint =  new Point(Math.min(currRoom.center.x + 1, width - 1), currRoom.upRoom.center.y);
                currRoom.upRoom.leftPoint =  new Point(Math.max(currRoom.center.x - 1, 0), currRoom.upRoom.center.y);
            }

            if (validateSpace(currRoom.upRoom)) {
                currRoom.upRoom.downRoom = currRoom;
                createSpace(currRoom.upRoom);
                world[currRoom.upRoom.downPoint.x][currRoom.upRoom.downPoint.y - 1] = Tileset.FLOOR;
                world[currRoom.upRoom.downPoint.x][currRoom.upRoom.downPoint.y] = Tileset.FLOOR;
            }
        }
        if (currRoom.rightRoom == null && currRoom.center.x < width - 1) {
            currRoom.rightRoom = new Space();
            currRoom.rightRoom.center = new Point(Math.min(currRoom.rightPoint.x + (hor / 2) + 1, width -1), currRoom.center.y);
            currRoom.rightRoom.leftPoint =  new Point(Math.max(currRoom.rightRoom.center.x - (hor / 2), 0), currRoom.rightRoom.center.y);
            currRoom.rightRoom.rightPoint =  new Point(Math.min(currRoom.rightRoom.center.x + (hor / 2), width - 1), currRoom.rightRoom.center.y);
            if (flipCoin()) { //Create a Room
                currRoom.rightRoom.upPoint = new Point(currRoom.center.x, Math.min(currRoom.rightRoom.center.y + (ver / 2), height -1));
                currRoom.rightRoom.downPoint = new Point(currRoom.center.x, Math.max(currRoom.rightRoom.center.y - (ver /2), 0));
            } else { //Create a Hall
                currRoom.rightRoom.upPoint = new Point(currRoom.center.x, Math.min(currRoom.rightRoom.center.y + 1, height -1));
                currRoom.rightRoom.downPoint = new Point(currRoom.center.x, Math.max(currRoom.rightRoom.center.y - 1, 0));
            }
            if (validateSpace(currRoom.rightRoom)) {
                currRoom.rightRoom.leftRoom = currRoom;
                createSpace(currRoom.rightRoom);
                world[currRoom.rightRoom.leftPoint.x - 1][currRoom.rightRoom.leftPoint.y] = Tileset.FLOOR;
                world[currRoom.rightRoom.leftPoint.x][currRoom.rightRoom.leftPoint.y] = Tileset.FLOOR;
            }

        }
        if (currRoom.downRoom == null && currRoom.center.y > 0) {
            currRoom.downRoom = new Space();
            currRoom.downRoom.center = new Point(Math.max(0,currRoom.center.x), Math.max(currRoom.downPoint.y - (ver / 2) - 1, 0));
            currRoom.downRoom.upPoint = new Point(currRoom.downRoom.center.x, Math.min(currRoom.downRoom.center.y + (ver / 2), height - 1));
            currRoom.downRoom.downPoint = new Point(currRoom.downRoom.center.x, Math.max(currRoom.downRoom.center.y - (ver / 2), 0));
            if (flipCoin()) { //create a Room
                currRoom.downRoom.rightPoint =  new Point(Math.min(currRoom.downRoom.center.x + (hor / 2), width - 1), currRoom.downRoom.center.y);
                currRoom.downRoom.leftPoint =  new Point(Math.max(currRoom.downRoom.center.x - (hor / 2), 0), currRoom.downRoom.center.y);
            } else { //crete a hall
                currRoom.downRoom.rightPoint =  new Point(Math.min(currRoom.downRoom.center.x + 1, width - 1), currRoom.downRoom.center.y);
                currRoom.downRoom.leftPoint =  new Point(Math.max(currRoom.downRoom.center.x - 1, 0), currRoom.downRoom.center.y);
            }
            if (validateSpace(currRoom.downRoom)) {
                currRoom.downRoom.upRoom = currRoom;
                createSpace(currRoom.downRoom);
                world[currRoom.downRoom.upPoint.x][currRoom.downRoom.upPoint.y + 1] = Tileset.FLOOR;
                world[currRoom.downRoom.upPoint.x][currRoom.downRoom.upPoint.y] = Tileset.FLOOR;
            }
        }
        if (currRoom.leftRoom == null && currRoom.center.x > 0) {
            currRoom.leftRoom = new Space();
            currRoom.leftRoom.center = new Point(Math.max(currRoom.leftPoint.x - (hor / 2) - 1, 0), currRoom.center.y);
            currRoom.leftRoom.leftPoint =  new Point(Math.max(currRoom.leftRoom.center.x - (hor / 2), 0), currRoom.leftRoom.center.y);
            currRoom.leftRoom.rightPoint =  new Point(Math.min(currRoom.leftRoom.center.x + (hor / 2), width - 1), currRoom.leftRoom.center.y);
            if (flipCoin()) { //Create a Room
                currRoom.leftRoom.upPoint = new Point(currRoom.center.x, Math.min(currRoom.leftRoom.center.y + (ver / 2), height -1));
                currRoom.leftRoom.downPoint = new Point(currRoom.center.x, Math.max(currRoom.leftRoom.center.y - (ver /2), 0));
            } else { //Create a Hall
                currRoom.leftRoom.upPoint = new Point(currRoom.center.x, Math.min(currRoom.leftRoom.center.y + 1, height -1));
                currRoom.leftRoom.downPoint = new Point(currRoom.center.x, Math.max(currRoom.leftRoom.center.y - 1, 0));
            }
            if (validateSpace(currRoom.leftRoom)) {
                currRoom.leftRoom.rightRoom = currRoom;
                createSpace(currRoom.leftRoom);
                world[currRoom.leftRoom.rightPoint.x][currRoom.leftRoom.rightPoint.y] = Tileset.FLOOR;
                world[currRoom.leftRoom.rightPoint.x + 1][currRoom.leftRoom.rightPoint.y] = Tileset.FLOOR;
            }
        }
    }

    private boolean validateSpace(Space temp) {
        if (temp.center.x == width || temp.center.x == 0 ||
                    temp.center.y == height || temp.center.y == 0) {
            return false;
        }
        Point c1 = new Point(Math.max(temp.leftPoint.x, 2), Math.max(temp.downPoint.y, 2));
        Point c2 = new Point(Math.min(temp.rightPoint.x, width - 2), Math.min(temp.upPoint.y, height - 2));
        if (Math.abs(c2.y - c1.y) < 2 || Math.abs(c2.x - c1.x) < 2) {
            return false;
        }
        if (validate(c1, c2)) {
            colorRoom(c1, c2);
            return true;
        }
        return false;
    }

    private void colorRoom(Point p1, Point p2) {
        for (int i = p1.x; i <= p2.x && i < width; i++) {
            for (int j = p1.y; j <= p2.y && j < height; j++) {
                if (i == p1.x || i == p2.x || j == p1.y || j == p2.y ||
                        i == width - 1 || j == height - 1) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

    public boolean flipCoin() { //Generate random chance
        int var = r.nextInt(2);
        return var == 0;
    }

    private boolean validate(Point p1, Point p2) {
        for (int i = p1.x; i <= p2.x && i < width - 1; i++) {
            for (int j = p1.y; j <= p2.y && j < height - 1; j++) {
                if (world[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public TETile[][] generateWorld() {
        for(int j = 0; j < width; j++) {
            for(int k = 0; k < height; k++) {
                world[j][k] = Tileset.NOTHING;
            }
        }
        createSpace(map);
        return world;
    }
}


