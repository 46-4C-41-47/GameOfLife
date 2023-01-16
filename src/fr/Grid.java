import java.awt.*;
import java.security.InvalidParameterException;
import java.util.Random;

public class Grid {
    public static final int ALIVE = 1, DEAD = 0, CIRCLE = 0, ALIVE_BORDER = 1, DEAD_BORDER = 2;
    private int edgesRules = CIRCLE;
    private Dimension size;
    private int[][] intGrid;


    public Grid(Dimension d){
        this.size = d;
        this.intGrid = new int[d.height][d.width];
        this.setup();
    }


    private void setup(){
        Random rand = new Random();

        for (int i = 0; i < size.height; i++) {
            for (int j = 0; j < size.width; j++) {
                intGrid[i][j] = Math.abs(rand.nextInt()) % 2;
            }
        }
    }


    public void next() {
        int[][] nextGrid = new int[size.height][size.width];

        for (int i = 0; i < size.height; i++) {
            for (int j = 0; j < size.width; j++) {
                if (intGrid[i][j] == DEAD) {
                    if (countNeighbor(i, j) == 3) {
                        nextGrid[i][j] = ALIVE;
                    }
                } else {
                    if (countNeighbor(i, j) < 2 || 3 < countNeighbor(i, j)) {
                        nextGrid[i][j] = DEAD;
                    } else {
                        nextGrid[i][j] = ALIVE;
                    }
                }
            }
        }

        intGrid = nextGrid;
    }


    public int countNeighbor(int x, int y) {
        int neigh;
        if (intGrid[x][y] == ALIVE) {
            neigh = -1;
        } else {
            neigh = 0;
        }

        return switch (edgesRules){
            case CIRCLE         -> circleEdges(x, y, neigh);
            case ALIVE_BORDER   -> aliveEdges(x, y, neigh);
            case DEAD_BORDER    -> deadEdges(x, y, neigh);
            default             -> -1;
        };
    }


    private int circleEdges(int x, int y, int count) {
        int neigh = count;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                neigh += intGrid[(size.height + x + i) % size.height][(size.width + y + j) % size.width];
            }
        }

        return neigh;
    }


    private int aliveEdges(int x, int y, int count) {
        int neigh = count;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i < 0 || size.height <= x + i || y + j < 0 || size.width <= y + j) {
                    neigh += 1;
                } else {
                    neigh += intGrid[x + i][y + j];
                }

            }
        }

        return neigh;
    }


    private int deadEdges(int x, int y, int count) {
        int neigh = count;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(x + i < 0 || size.height <= x + i || y + j < 0 || size.width <= y + j)) {
                    neigh += intGrid[x + i][y + j];
                }
            }
        }

        return neigh;
    }


    public int[][] getGrid() {
        return intGrid;
    }


    public Dimension getDimension(){
        return size;
    }


    public void setEdgesRules(int rules) throws InvalidParameterException {
        if (rules != CIRCLE && rules != ALIVE_BORDER && rules != DEAD_BORDER){
            throw new InvalidParameterException();
        } else {
            edgesRules = rules;
        }
    }


    @Override
    public String toString(){
        String printableGrid = "";

        for (int i = 0; i < size.height; i++) {
            for (int j = 0; j < size.width; j++) {
                printableGrid += intGrid[i][j]+",";
            }
            printableGrid += "\n";
        }

        return printableGrid;
    }
}
