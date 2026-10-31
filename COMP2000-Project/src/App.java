import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Label;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Frame window = new Frame("Gravity Simulation");
        SimulationPanel panel = new SimulationPanel();
        ControlPanel controlPanel = new ControlPanel();

        panel.getPlanetName()
            .forEach(planet -> controlPanel.planetChoice.add(planet));
        
        panel.getShip().addObserver(controlPanel);

        window.setLayout(new BorderLayout());

        window.add(panel, BorderLayout.CENTER);
        window.add(controlPanel, BorderLayout.EAST);

        window.setSize(1200, 900);
        window.setLocationRelativeTo(null);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        window.setVisible(true);

        controlPanel.engineButton.addMouseListener(new MouseAdapter()  {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.getShip().isThrusting = true;
            }
            @Override 
            public void  mouseReleased(MouseEvent e) {
                panel.getShip().isThrusting = false;
            }
        });

        controlPanel.applyButton.addActionListener(e -> {
            try {
                double angle = Double.parseDouble(controlPanel.angleField.getText());
                float launchForce = Float.parseFloat(controlPanel.launchField.getText());
                float force = Float.parseFloat(controlPanel.forceField.getText());
 
                // collect the error messages from the setters
                List<String> errors = new ArrayList<>();
 
                try {
                    panel.getShip().setAngle(angle);
                } catch (InvalidSettingException ex) {
                    errors.add(ex.getMessage());
                }
                try {
                    panel.getShip().setLaunchForce(launchForce);
                } catch (InvalidSettingException ex) {
                    errors.add(ex.getMessage());
                }
                try {
                    panel.getShip().setEngineForce(force);
                } catch (InvalidSettingException ex) {
                    errors.add(ex.getMessage());
                }
 
                // show the messages on the status lines
                Label[] lines = { controlPanel.statusLine1, controlPanel.statusLine2, controlPanel.statusLine3 };
                for (Label line : lines) {
                    line.setText("");
                }
 
                if (errors.isEmpty()) {
                    lines[0].setText("Settings applied successfully.");
                } else {
                    for (int i = 0; i < errors.size(); i++) {
                        lines[i].setText(errors.get(i));
                    }
                }
 
            } catch (NumberFormatException ex) {
                controlPanel.statusLine1.setText("Invalid number");
                controlPanel.statusLine2.setText("Please enter valid numbers.");
                controlPanel.statusLine3.setText("");
            }
        });

        controlPanel.startButton.addActionListener(e -> panel.startSimulation());
        controlPanel.pauseButton.addActionListener(e -> panel.pauseSimulation());
        controlPanel.resetButton.addActionListener(e -> {
            panel.resetSimulation();
            panel.getShip().addObserver(controlPanel);
            controlPanel.statusLine1.setText("");
            controlPanel.statusLine2.setText("");
            controlPanel.statusLine3.setText("");

            panel.repaint();
        });

        while(true) {
            panel.updatePhysics();
  
            Double distance = panel.getPlanetDistances().get(controlPanel.planetChoice.getSelectedItem());
            if (distance != null) {
                controlPanel.distanceLabel.setText("Distance: " + distance.intValue());
            }
 
            panel.repaint();
 
            try {
                Thread.sleep(33);
 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
