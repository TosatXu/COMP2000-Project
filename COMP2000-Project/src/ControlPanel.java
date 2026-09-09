import java.awt.*;

public class ControlPanel extends Panel {
    public TextField angleField;
    public TextField forceField;
    public TextField launchField;

    public Label fuelLabel;
    private int fuelPercentage = 100;

    public Button engineButton;
    public Button applyButton;

    public Button startButton;
    public Button pauseButton;
    public Button resetButton;

    public Label statusLine1;
    public Label statusLine2;
    public Label statusLine3;


    public ControlPanel() {
        setLayout(null);
        setBackground(new Color(25, 25, 40));
        
        // Title label
        Label title = new Label("Spaceship Control Panel");
        title.setBounds(20, 30, 250, 20);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        // Launch Angle label and field
        Label angleLabel = new Label("Launch Angle (0 - 90)");
        angleLabel.setBounds(20, 60, 170, 20);
        angleLabel.setForeground(Color.WHITE);
        add(angleLabel);

        
        angleField = new TextField("45");
        angleField.setBounds(20, 85, 180, 25);
        add(angleField);

        // Launch Force label and field
        Label launchLabel = new Label("Launch Force (1000 - 10000)");
        launchLabel.setBounds(20, 115, 170, 20);
        launchLabel.setForeground(Color.WHITE);
        add(launchLabel);

        launchField = new TextField("4000");
        launchField.setBounds(20, 140, 180, 25);
        add(launchField);


        // Engine Force label and field
        Label forceLabel = new Label("Engine Force (1 - 100)");
        forceLabel.setBounds(20, 170, 170, 20);
        forceLabel.setForeground(Color.WHITE);
        add(forceLabel);

        forceField = new TextField("40");
        forceField.setBounds(20, 195, 180, 25);
        add(forceField);

        applyButton = new Button("Apply Settings");
        applyButton.setBounds(20, 240, 100, 30);
        add(applyButton);

        // Fuel
        fuelLabel = new Label("Fuel: 1000");
        fuelLabel.setForeground(Color.GREEN);
        fuelLabel.setBounds(20, 280, 100, 20);
        add(fuelLabel);

        // Fire Engine
        engineButton = new Button("Fire Engine");
        engineButton.setBounds(20, 340, 100, 30);
        add(engineButton);


        startButton = new Button("Start");
        startButton.setBounds(20, 380, 100, 30);
        add(startButton);

        pauseButton = new Button("Pause");
        pauseButton.setBounds(20, 420, 100, 30);
        add(pauseButton);

        resetButton = new Button("Reset");
        resetButton.setBounds(20, 460, 100, 30);
        add(resetButton);

        // Status labels
        Label statusTitle = new Label("Status:");
        statusTitle.setBounds(20, 500, 100, 20);
        statusTitle.setForeground(Color.WHITE);
        add(statusTitle);

        statusLine1 = new Label("");
        statusLine1.setBounds(20, 520, 250, 20);
        statusLine1.setForeground(Color.WHITE);
        add(statusLine1);

        statusLine2 = new Label("");
        statusLine2.setBounds(20, 540, 250, 20);
        statusLine2.setForeground(Color.WHITE);
        add(statusLine2);

        statusLine3 = new Label("");
        statusLine3.setBounds(20, 560, 250, 20);
        statusLine3.setForeground(Color.WHITE);
        add(statusLine3);

    }

    public void setFuelPercentage(int fuelPercentage) {
        this.fuelPercentage = fuelPercentage;
        repaint();
    }

    @Override 
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.drawLine(0, 0, 0, getHeight());

        // Fuel bar
        g.setColor(Color.WHITE);
        g.drawRect(20, 300, 100, 20);

        if (fuelPercentage > 50) {
            g.setColor(Color.GREEN);
        } else if (fuelPercentage > 25) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.RED);
        }
        // Fill fuel bar
        g.fillRect(20, 300, 100 * fuelPercentage / 100, 20);
    }

    @Override 
    public Dimension getPreferredSize() {
        return new Dimension(300, 600);
    }
}
