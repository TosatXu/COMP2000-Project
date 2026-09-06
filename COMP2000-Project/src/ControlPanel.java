import java.awt.*;

public class ControlPanel extends Panel {
    public TextField angleField;
    public TextField forceField;

    public Button engineButton;

    public Button startButton;
    public Button pauseButton;
    public Button resetButton;

    public ControlPanel() {
        setLayout(new GridLayout(5,1, 5, 5));

        add(new Label("Launch Angle (0 - 90)"));
        angleField = new TextField("45");
        add(angleField);

        add(new Label("Engine Force (1 - 100)"));

        forceField = new TextField("40");
        add(forceField);

        engineButton = new Button("Fire Engine");
        add(engineButton);

        startButton = new Button("Start");
        add(startButton);

        pauseButton = new Button("Pause");
        add(pauseButton);

        resetButton = new Button("Reset");
        add(resetButton);
    }
}
