import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;


public class Ihm extends JPanel {
    private int frameRate = 15;
    private boolean runningStatus = false;
    private Timer timer;
    private JPanel[][] panelGrid;
    private Grid grid;
    private DrawerTask drawer;
    private JButton button;

    private MainMenu frame;
    private Canvas canvas;


    public Ihm(Grid g, MainMenu frame) {
        super();
        grid = g;
        this.canvas = new Canvas(Toolkit.getDefaultToolkit().getScreenSize(), g, 1);
        this.frame = frame;
        this.setLayout(new BorderLayout());
        this.setup();
        this.setActionListener();
        drawer = new DrawerTask(grid, this);
    }


   private void setup() {
        JPanel southPanel = new JPanel();
        button = new JButton("Pause");
        southPanel.add(button);

        Ihm temp = this;

        JButton menu = new JButton("Go back to Main Menu");
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseTask();
                frame.getCardLayout().first(frame.getContentPane());
                temp.update(temp.getGraphics());
            }
        });
        southPanel.add(menu);

        this.add(southPanel, BorderLayout.SOUTH);
        this.add(canvas, BorderLayout.CENTER);
    }


    private void setActionListener() {
        button.addActionListener(e -> {
            if (runningStatus) {
                this.pauseTask();
            } else {
                this.resumeTask();
            }
        });
    }


    private void resumeTask() {
        if (!runningStatus) {
            button.setText("Pause");
            runningStatus = true;
            this.run();
        }
    }


    private void pauseTask() {
        if (runningStatus) {
            button.setText("Resume");
            runningStatus = false;
            timer.cancel();
        }
    }


    public void run() {
        runningStatus = true;
        timer = new Timer(true);
        drawer = new DrawerTask(grid, this);
        timer.scheduleAtFixedRate(drawer, 0, 1000/frameRate);
    }


    public DrawerTask getDrawer() {
        return drawer;
    }


    public Canvas getCanvas() {
        return canvas;
    }


    public void setFrameRate(int frameRate) throws IllegalArgumentException {
        if (frameRate < 1){
            throw new IllegalArgumentException();
        } else {
            this.frameRate = frameRate;
        }
    }
}
