import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Canvas extends JPanel {
    private BufferedImage offscreenImage;
    private Graphics2D offscreenGraphics;
    private Dimension pixel_size;
    private Grid grid;
    private int insets;


    public Canvas(Dimension d, Grid g, int insets) {
        pixel_size = d;
        grid = g;
        this.insets = insets;
        offscreenImage = new BufferedImage(pixel_size.width, pixel_size.height, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = (Graphics2D) offscreenImage.getGraphics();
    }


    @Override
    public void update(Graphics g) {
        //super.update(g);
        paint(g);
    }


    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        offscreenGraphics.setColor(Color.LIGHT_GRAY);
        offscreenGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawGame(offscreenGraphics);
        g.drawImage(offscreenImage, 0, 0, null);
    }


    private void drawGame(Graphics g) {
        int square_width  = pixel_size.width  / grid.getDimension().width, square_height = pixel_size.height / grid.getDimension().height;
        int[][] int_grid  = grid.getGrid();

        g.setColor(Color.BLACK);

        for (int i = 0; i < grid.getDimension().height; i++) {
            for (int j = 0; j < grid.getDimension().width; j++) {
                if (int_grid[i][j] == Grid.ALIVE) {
                    g.fillRect(j * square_width, i * square_height, square_width, square_height);
                }
            }
        }
    }
}
