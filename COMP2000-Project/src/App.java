import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App {
    public static void main(String[] args) throws Exception {
        Frame window = new Frame("Gravity Simulation");
        SimulationPanel panel = new SimulationPanel();
        ControlPanel controlPanel = new ControlPanel();

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
                
                // clear previous error messages
                controlPanel.statusLine1.setText("");
                controlPanel.statusLine2.setText("");
                controlPanel.statusLine3.setText("");

                int errorCount = 0;
                if (angle < 0 || angle > 90) {
                    errorCount++;
                    if(errorCount == 1) {
                        controlPanel.statusLine1.setText("Invalid angle");
                    }
                }

                if (launchForce < 1000 || launchForce > 10000) {
                    errorCount++;
                    if(errorCount == 1) {
                        controlPanel.statusLine1.setText("Invalid launch force");
                    } else if (errorCount == 2) {
                        controlPanel.statusLine2.setText("Invalid launch force");
                    }
                }

                if (force < 1 || force > 100) {
                    errorCount++;
                    if(errorCount == 1) {
                        controlPanel.statusLine1.setText("Invalid engine force");
                    } else if (errorCount == 2) {
                        controlPanel.statusLine2.setText("Invalid engine force");
                    } else {
                        controlPanel.statusLine3.setText("Invalid engine force");
                    }
                }
                if (errorCount > 0) {
                    return;
                }

                panel.getShip().setAngle(angle);
                panel.getShip().setLaunchForce(launchForce);
                panel.getShip().setEngineForce(force);

                controlPanel.statusLine1.setText("Settings applied successfully.");

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
            panel.repaint();
        });

        while(true) {
            panel.updatePhysics();

            //update fuel percentage in control panel
            int currentFuel = panel.getShip().getFuel();
            int maxFuel = panel.getShip().getMaxFuel();
            controlPanel.fuelLabel.setText("Fuel: " + currentFuel + "/" + maxFuel);
            int percent = (currentFuel * 100) / maxFuel;
            controlPanel.setFuelPercentage(percent);

            panel.repaint();

            try {
                Thread.sleep(33);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
