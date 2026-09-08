import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class SimulationPanel extends Panel{
    private Spaceship ship;
    private Planet earth;
    private Planet mars;
    private boolean isRunning = false;
    private ArrayList<SimulationObject> objects = new ArrayList<SimulationObject>();

    public SimulationPanel(){
        resetSimulation();

    }

    public void resetSimulation(){
        ship = new Spaceship(500f, 12, 600, 450, 1000,0f, 200.0, 5500f);
        earth = new Planet(50000000000000f, 100, 600, 500);
        mars = new Planet(30000000000000f, 70, 800, 300);

        objects.clear();
        objects.add(earth);
        objects.add(mars);
        objects.add(ship);

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

        //Calculate the gravitational force between every object in the simulation
        for (int i = 0; i < objects.size(); i++) {
            for (int j = 0; j < objects.size(); j++) {
                if (i != j) {
                    objects.get(i).Gravity(objects.get(j));
                }
            }
        }

        ship.Fly();
        ship.AdjustAngle();
        ship.moveObject();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        PaintPlanet(Color.blue, earth, g);
        PaintPlanet(Color.orange, mars, g);

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
        g2d.rotate(-ship.angle);
        g2d.setColor(Color.RED);

        int[] shipPointsX = { shipHalf+2, -shipHalf, -shipHalf };
        int[] shipPointsY = { 0, -shipHalf, shipHalf };
        
        g2d.fillPolygon(shipPointsX, shipPointsY, 3);
        g2d.dispose();

    }
    
    public Spaceship getShip() {
        return ship;
    }

    public void PaintPlanet (Color colour, Planet planet, Graphics g) {
        g.setColor(colour);
        int planetX = (int) (planet.coordinates[0] - planet.size / 2);
        int planetY = (int) (planet.coordinates[1] - planet.size / 2);
        g.fillOval(planetX, planetY, planet.size, planet.size);
        System.out.println(planet.coordinates[0] + ", " + planet.coordinates[1]);
    }
}
