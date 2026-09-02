import java.awt.*;

public class ControlPanel extends Panel {
    public TextField angleField;
    public TextField forceField;

    public Button engineButton;

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
    }
}
