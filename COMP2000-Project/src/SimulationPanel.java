import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;

public class SimulationPanel extends Panel{
    private Spaceship ship;
    private Planet earth;
    private boolean isRunning = false;

    public SimulationPanel(){
        resetSimulation();

    }

    public void resetSimulation(){
        ship = new Spaceship(500f, 12, 400, 250, 30,40f, 45.0, 4500f);
        earth = new Planet(50000000000000f, 100, 400, 300);
        isRunning = false;

    }

    public void startSimulation(){
        isRunning = true;
    }

    public void pauseSimulation(){
        isRunning = false;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void updatePhysics() {
        if(isRunning == false) {
            return;
        }
        ship.Gravity(earth);
        ship.Fly();
        ship.AdjustAngle();
        ship.moveObject();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLUE);
        int earthX = (int) (earth.coordinates[0] - earth.size / 2);
        int earthY = (int) (earth.coordinates[1] - earth.size / 2);
        g.fillOval(earthX, earthY, earth.size, earth.size);

        
        // g.setColor(Color.RED);
        // int shipCenterX = (int) ship.coordinates[0];
        // int shipCenterY = (int) ship.coordinates[1];
        // int shipHalf = ship.size/2;
        // int[]shipXPoints = { shipCenterX, shipCenterX - shipHalf, shipCenterX + shipHalf };
        // int[]shipYPoints = { shipCenterY - shipHalf, shipCenterY + shipHalf, shipCenterY + shipHalf };

        // g.fillPolygon(shipXPoints, shipYPoints, 3);
        Graphics2D g2d = (Graphics2D) g.create();

        int shipX = (int) ship.coordinates[0];
        int shipY = (int) ship.coordinates[1];
        int shipHalf = ship.size/2;

        g2d.translate(shipX, shipY);
        g2d.rotate(ship.angle);
        g2d.setColor(Color.RED);

        int[] shipPointsX = { shipHalf, -shipHalf, -shipHalf };
        int[] shipPointsY = { 0, -shipHalf, shipHalf };
        
        g2d.fillPolygon(shipPointsX, shipPointsY, 3);
        g2d.dispose();

    }
    
    public Spaceship getShip() {
        return ship;
    }
}
