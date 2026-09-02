import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Color;

public class SimulationPanel extends Panel{
    private Spaceship ship;
    private Planet earth;


    public SimulationPanel(){
        ship = new Spaceship(500f, 12, 400, 250, 30,40f, 45.0, 4500f);
        earth = new Planet(50000000000000f, 100, 400, 300);

    }

    public void updatePhysics() {
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

        g.setColor(Color.RED);
        int shipX = (int) (ship.coordinates[0] - ship.size / 2);
        int shipY = (int) (ship.coordinates[1] - ship.size /2);
        g.fillOval(shipX, shipY, ship.size, ship.size);

    }
    
    public Spaceship getShip() {
        return ship;
    }
}
