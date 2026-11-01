import java.awt.Panel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.function.Supplier;


public class SimulationPanel extends Panel{
    private Spaceship ship;
    private boolean isRunning = false;

    private ArrayList<SimulationObject> objects = new ArrayList<SimulationObject>();
    
    private float[][] stars = new float[150][3];
    
    private Map<String, Supplier<Spaceship>> shipTypes = new LinkedHashMap<>();
    private String selectedShipType = "Rocket";

    private Image offscreenImage;
    private Graphics offscreenGraphics;

    public SimulationPanel(){
        shipTypes.put("Rocket", () -> new Rocket(400, 420));
        shipTypes.put("Fighter", () -> new Fighter(400, 420));
        shipTypes.put("Cargo", () -> new Cargo(400, 420));

        Random random = new Random(7);
        for (int i = 0; i < stars.length; i++) {
            stars[i][0] = random.nextFloat();
            stars[i][1] = random.nextFloat();
            stars[i][2] = random.nextFloat();
        }
        
        resetSimulation();
    }

    public void resetSimulation(){
        ship = shipTypes.get(selectedShipType).get();

        objects.clear();
        objects.add(0, ship);
        objects.add(new Planet("Earth", 50000000000000f, 100, 400, 500, Color.BLUE));
        objects.add(new Planet("Mars", 30000000000000f, 70, 600, 300, Color.ORANGE));
        objects.add(new Planet("Moon", 10000000000000f, 40, 200, 250, Color.LIGHT_GRAY));
        objects.add(new Planet("Jupiter", 80000000000000f, 130, 720, 650, new Color(200, 150, 100)));
        
        isRunning = false;
    }

    public void startSimulation(){
        isRunning = true;
    }

    public void pauseSimulation(){
        isRunning = false;
    }

    public List<String> getShipTypeName() {
        return shipTypes.keySet().stream().collect(Collectors.toList());
    }

    public void setShipType(String name) {
        selectedShipType = name;
    }

    public boolean isRunning(){
        return isRunning;
    }
 
    public void updatePhysics() {
        if(isRunning == false) {
            return;
        }

        if (!ship.isCrashed()) {
            //Calculate the gravitational force between the ship and every planet
            getPlanets().forEach(planet -> ship.Gravity(planet));
 
            //Check ship collision with every planet and crash into the first one hit
            getPlanets().stream()
                    .filter(planet -> ship.checkCollision(ship, planet))
                    .findFirst()
                    .ifPresent(planet -> ship.crash(planet.getName()));
        }
 
        ship.Fly();
        ship.AdjustAngle();
        
        for (SimulationObject object : objects) {
            object.moveObject();
        }
    }
 
    public <T extends SimulationObject> List<T> getObjectsOfType(Class<T> type) {
        return objects.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }
    
    public List<Planet> getPlanets() {
        return getObjectsOfType(Planet.class);
    }
 
    public List<String> getPlanetName() {
        return getPlanets().stream()
                .map(Planet::getName)
                .collect(Collectors.toList());
    }
 
    public List<Spaceship> getSpaceships() {
        return getObjectsOfType(Spaceship.class);
    }
 
    public Map<String, Double> getPlanetDistances() {
        return getPlanets().stream()
                .collect(Collectors.toMap(
                        Planet::getName,
                        planet -> ship.CalculateDistance(ship.coordinates, planet.coordinates)));
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

        for (int i = 0; i < stars.length; i++) {
            int x = (int) (stars[i][0] * getWidth());
            int y = (int) (stars[i][1] * getHeight());

            int size = (stars[i][2] > 0.8f) ? 4 : 1;
            int brightness = (int) (155 + stars[i][2] * 100);

            offscreenGraphics.setColor(new Color(brightness, brightness, brightness));
            offscreenGraphics.fillOval(x, y, size, size);
        }

        getPlanets().forEach(planet -> planet.draw(offscreenGraphics));

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

        g2d.setColor(ship.colour);

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
