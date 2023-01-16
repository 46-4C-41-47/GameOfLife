import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension MINIMUM_SCREEN_SIZE = new Dimension(SCREEN_SIZE.width/2, SCREEN_SIZE.height/2);
    private static final Dimension DEFAULT_SCREEN_DIMENSION = new Dimension(830, 480);
    private static final Dimension DEFAULT_GRID_DIMENSION = new Dimension(100, 56);
    private static final String[] EDGES_RULES_NAME = {"Circular", "Alive", "Dead"};
    private static final int DEFAULT_FRAMERATE = 15;

    private final int textFieldSize = 15;
    private Insets myInsets = new Insets(10, 10, 10, 10);
    private CardLayout cards =new CardLayout();
    private JLabel indication[];
    private JTextField input[];
    private JComboBox combo;

    private Grid grid;


    public MainMenu() {
        super();
        this.setSize(DEFAULT_SCREEN_DIMENSION);
        this.setMinimumSize(MINIMUM_SCREEN_SIZE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(cards);
        this.add(createFrame());
        this.setVisible(true);
    }


    private JPanel createFrame() {
        JPanel mainPanel = new JPanel(), centerPanel = new JPanel();
        JButton confirm = new JButton("Confirm");
        combo = new JComboBox(EDGES_RULES_NAME);

        mainPanel.setLayout(new BorderLayout());
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = myInsets;

        indication = new JLabel[]{
                new JLabel("width :"),
                new JLabel("heigth :"),
                new JLabel("Iteration/second :"),
                new JLabel("World's edges rules :")
        };

        input = new JTextField[]{
                new JTextField(""+DEFAULT_GRID_DIMENSION.width, textFieldSize),
                new JTextField(""+DEFAULT_GRID_DIMENSION.height, textFieldSize),
                new JTextField(""+DEFAULT_FRAMERATE, textFieldSize)
        };

        for (int i = 0; i < input.length; i++) {
            gbc.gridx     = 0;
            gbc.gridy     = i;
            indication[i].setHorizontalAlignment(SwingConstants.RIGHT);
            centerPanel.add(indication[i] , gbc);

            gbc.gridx     = 1;
            gbc.gridy     = i;
            input[i].setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(input[i] , gbc);
        }

        gbc.gridx     = 0;
        gbc.gridy     = 3;
        indication[indication.length-1].setHorizontalAlignment(SwingConstants.RIGHT);
        centerPanel.add(indication[indication.length-1] , gbc);

        gbc.gridx     = 1;
        gbc.gridy     = 3;
        centerPanel.add(combo , gbc);

        gbc.gridwidth = 2;
        gbc.gridx     = 0;
        gbc.gridy     = 4;
        centerPanel.add(confirm , gbc);

        confirm.addActionListener(this::confirmActionListener);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        return mainPanel;
    }


    private void confirmActionListener(ActionEvent event) {
        int values[] = new int[input.length];

        try {
            for (int i = 0; i < input.length; i++) {
                values[i] = Integer.parseInt(input[i].getText());
                if (values[i] < 1) {
                    throw new IllegalArgumentException();
                }
            }

            grid = new Grid(new Dimension(values[0], values[1]));
            grid.setEdgesRules(combo.getSelectedIndex());
            Ihm gui = new Ihm(grid, this);
            gui.setFrameRate(values[2]);
            this.add(gui);
            cards.last(this.getContentPane());
            gui.run();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Met des chiffres la putain de ta mère");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Non la tu forces");
        }
    }


    public CardLayout getCardLayout() {
        return cards;
    }
}
