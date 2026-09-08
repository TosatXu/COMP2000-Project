import java.awt.*;

public class ControlPanel extends Panel {
    public TextField angleField;
    public TextField forceField;

    public Button engineButton;

    public Button startButton;
    public Button pauseButton;
    public Button resetButton;

    public ControlPanel() {
        setLayout(null);
        setBackground(new Color(25, 25, 40));
        
        // Title label
        Label title = new Label("Spaceship Control Panel");
        title.setBounds(20, 30, 250, 25);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        // Launch Angle label and field
        Label angleLabel = new Label("Launch Angle (0 - 90)");
        angleLabel.setBounds(20, 80, 170, 20);
        angleLabel.setForeground(Color.WHITE);
        add(angleLabel);

        
        angleField = new TextField("45");
        angleField.setBounds(20, 105, 180, 25);
        add(angleField);

        // Engine Force label and field
        Label forceLabel = new Label("Engine Force (1 - 100)");
        forceLabel.setBounds(20, 150, 170, 20);
        forceLabel.setForeground(Color.WHITE);
        add(forceLabel);

        forceField = new TextField("40");
        forceField.setBounds(20, 175, 180, 25);
        add(forceField);

        engineButton = new Button("Fire Engine");
        engineButton.setBounds(20, 230, 100, 30);
        add(engineButton);

        startButton = new Button("Start");
        startButton.setBounds(20, 290, 100, 30);
        add(startButton);

        pauseButton = new Button("Pause");
        pauseButton.setBounds(20, 350, 100, 30);
        add(pauseButton);

        resetButton = new Button("Reset");
        resetButton.setBounds(20, 410, 100, 30);
        add(resetButton);
    }

    @Override 
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);

        g.drawLine(0, 0, 0, getHeight());
    }

    @Override 
    public Dimension getPreferredSize() {
        return new Dimension(300, 600);
    }
}
