package byow.lab12;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hexagon {
    int xCord;
    int yCord;
    int size;
    TETile[][] dim;

    public Hexagon(int s){
        size = s;
        xCord = ((s - 2) * 3) + 4;
        yCord = s * 2;
        dim = new TETile[xCord][yCord];
    }

    public void setColor(TETile color) {
        int startPoint = (xCord / 2) - (size / 2);
        int finalPoint = startPoint + size - 1;
        for (int i = 0; i < xCord; i++) {
            for (int j = 0; j < yCord; j++) {
                if (j > (xCord / 2) - 1) {
                    if (j - i > finalPoint || i - j < startPoint) {
                        dim[i][j] = color;
                    } else {
                        dim[i][j] = Tileset.NOTHING;
                    }
                } else {
                    if (i + j < startPoint || i - j > finalPoint) {
                        dim[i][j] = Tileset.NOTHING;
                    } else {
                        dim[i][j] = color;
                    }
                }
            }
        }


    }

    public TETile getColor(int x, int y){
        return dim[x][y];
    }
}
