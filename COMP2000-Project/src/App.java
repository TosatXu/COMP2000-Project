import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App {
    public static void main(String[] args) throws Exception {
        Frame window = new Frame("Gravity Simulation");
        SimulationPanel panel = new SimulationPanel();

        window.add(panel);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        window.setVisible(true);

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
