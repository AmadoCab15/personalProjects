package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */

public class HexWorld {

    private static final int WIDTH = 30;
    private static final int HEIGHT = 40;


    public static void addHexagon(int start) {


    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Hexagon hex = new Hexagon(5);
        hex.setColor(Tileset.GRASS);
        for(int i = 0; i < hex.xCord; i++) {
            for(int j = 0; j < hex.yCord; j++) {
                world[i][j] = hex.getColor(i, j);
            }
        }
        ter.renderFrame(world);

    }

}
