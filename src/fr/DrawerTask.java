import java.util.TimerTask;

public class DrawerTask extends TimerTask {
    private Ihm gui;
    private Grid grid;
    private int iteration = 0;


    public DrawerTask(Grid g, Ihm gui) {
        this.grid = g;
        this.gui = gui;
    }


    @Override
    public void run() {
        grid.next();
        gui.getCanvas().update(gui.getCanvas().getGraphics());
        iteration++;
    }


    public int getIteration() {
        return iteration;
    }
}
