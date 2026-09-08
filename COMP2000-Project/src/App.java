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

        window.setSize(800, 600);
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
            try {
                double angle = Double.parseDouble(controlPanel.angleField.getText());
                float force = Float.parseFloat(controlPanel.forceField.getText());
                
                if(angle < 0 || angle > 90) {
                    System.out.println("Launch angle must be between 0 and 90 degrees.");
                    return;
                }
                if(force < 1 || force > 100) {
                    System.out.println("Engine force must be between 1 and 100.");
                    return;
                }

                panel.getShip().setAngle(angle);
                panel.getShip().setEngineForce(force);
                panel.getShip().isThrusting = true;
            } catch (NumberFormatException ex) {
                System.err.println("Invalid input, please enter valid numbers.");
            
            }
        }

            @Override 
            public void  mouseReleased(MouseEvent e) {
                panel.getShip().isThrusting = false;
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
            panel.repaint();

            try {
                Thread.sleep(33);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
