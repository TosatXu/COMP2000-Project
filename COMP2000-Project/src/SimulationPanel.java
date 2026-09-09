import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Image;

import Algorithms.NoMovement;

public class SimulationPanel extends Panel{
    private Spaceship ship;
    private Planet earth;
    private Planet mars;
    private boolean isRunning = false;
    private ArrayList<SimulationObject> objects = new ArrayList<SimulationObject>();
    private Image offscreenImage;
    private Graphics offscreenGraphics;

    public SimulationPanel(){
        resetSimulation();

    }

    public void resetSimulation(){
        ship = new Spaceship(500f, 12, 400, 420, 1000,10f, 90.0, 4000f);
        earth = new Planet(50000000000000f, 100, 400, 500);
        mars = new Planet(30000000000000f, 70, 600, 300);

        objects.clear();
        objects.add(0, ship);
        objects.add(earth);
        objects.add(mars);
        
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

        for (int i = 1; i < objects.size(); i++) {
            //Calculate the gravitational force between the ship and every object in the simulation
            ship.Gravity(objects.get(i));
        
            //Check ship collision with every other object
            if (ship.checkCollision(ship, objects.get(i)))
                ship.changeMovement(new NoMovement());
        }

        ship.Fly();
        ship.AdjustAngle();
        
        for (SimulationObject object : objects) {
            object.moveObject();
        }
    }

    @Override 
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if(offscreenImage == null || offscreenImage.getWidth(this) != getWidth() || offscreenImage.getHeight(this) != getHeight()) {
            offscreenImage = createImage(getWidth(), getHeight());
            if(offscreenImage != null) {
                offscreenGraphics = offscreenImage.getGraphics();
            }
        }
        if(offscreenGraphics == null) {
            return;
        }
        
        offscreenGraphics.setColor(Color.BLACK);
        offscreenGraphics.fillRect(0,0, getWidth(), getHeight());

        PaintPlanet(Color.blue, earth, offscreenGraphics);
        PaintPlanet(Color.orange, mars, offscreenGraphics);

        Graphics2D g2d = (Graphics2D) offscreenGraphics.create();

        int shipX = (int) ship.coordinates[0];
        int shipY = (int) ship.coordinates[1];
        int shipHalf = ship.size/2;

        g2d.translate(shipX, shipY);
        g2d.rotate(-ship.angle);

        if(ship.isThrusting == true) {
            g2d.setColor(Color.ORANGE);
            int[] flameX = { -shipHalf, -shipHalf -10, -shipHalf };
            int[] flameY = { -shipHalf/2, 0, shipHalf/2 };
            g2d.fillPolygon(flameX, flameY, 3);
        }

        g2d.setColor(Color.RED);

        int[] shipPointsX = { shipHalf+2, -shipHalf, -shipHalf };
        int[] shipPointsY = { 0, -shipHalf, shipHalf };
        
        g2d.fillPolygon(shipPointsX, shipPointsY, 3);
        g2d.dispose();

        g.drawImage(offscreenImage, 0, 0, this);
        
    }
    
    public Spaceship getShip() {
        return ship;
    }

    public void PaintPlanet (Color colour, Planet planet, Graphics g) {
        g.setColor(colour);
        int planetX = (int) (planet.coordinates[0] - planet.size / 2);
        int planetY = (int) (planet.coordinates[1] - planet.size / 2);
        g.fillOval(planetX, planetY, planet.size, planet.size);
        //System.out.println(planet.coordinates[0] + ", " + planet.coordinates[1]);
    }
}
