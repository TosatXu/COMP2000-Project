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
    private String launchPlanet = "Earth";
    private static final int earthX = 180;
    private static final int earthY = 720;

    private Spaceship ship;
    private boolean isRunning = false;
    private String targetPlanet = "Mars";

    private Asteroid asteroid1;


    private ArrayList<SimulationObject> objects = new ArrayList<SimulationObject>();
    
    private float[][] stars = new float[150][3];
    
    private Map<String, Supplier<Spaceship>> shipTypes = new LinkedHashMap<>();
    private String selectedShipType = "Rocket";

    private Image offscreenImage;
    private Graphics offscreenGraphics;

    public SimulationPanel(){
        shipTypes.put("Rocket", () -> new Rocket(earthX, earthY - 60));
        shipTypes.put("Fighter", () -> new Fighter(earthX, earthY - 60));
        shipTypes.put("Cargo", () -> new Cargo(earthX, earthY - 60));

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
        objects.add(new Planet("Earth", 50000000000000f, 100, earthX, earthY, Color.BLUE));
        objects.add(new Planet("Mars", 30000000000000f, 70, 640, 540, Color.ORANGE));
        objects.add(new Planet("Moon", 10000000000000f, 40, 300, 400, Color.LIGHT_GRAY));
        objects.add(new Planet("Jupiter", 80000000000000f, 130, 780, 150, new Color(200, 150, 100)));
        asteroid1 = new Asteroid(100000f, 25, 200, 150, 1.5, 1.0, Color.LIGHT_GRAY);
        objects.add(asteroid1);
        
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

    public List<String> getDestinationName() {
        return getPlanets().stream()
                .map(Planet::getName)
                .filter(name -> !name.equals(launchPlanet))
                .collect(Collectors.toList());
    }

    public void setTargetPlanet(String name) {
        targetPlanet = name;
    }

    public String getTargetPlanet() {
        return targetPlanet;
    }

    public boolean isRunning(){
        return isRunning;
    }
 
    public void updatePhysics() {
        if(isRunning == false) {
            return;
        }

        if (!ship.isFinished()) { // changed to finished check to allow for landing on planets
            //Calculate the gravitational force between the ship and every planet
            getPlanets().forEach(planet -> ship.Gravity(planet));
 
            //Check ship collision with every planet and crash into the first one hit
            getPlanets().stream()
                    .filter(planet -> ship.checkCollision(ship, planet))
                    .findFirst()
                    .ifPresent(planet -> {
                        if (planet.getName().equals(targetPlanet)) {
                            ship.arrive(planet.getName());
                        } else {
                            ship.crash(planet.getName());
                        }
                    });
        }
 
        ship.Fly();
        ship.AdjustAngle();

        for(SimulationObject obj : objects) {
            if(obj instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) obj;
                getPlanets().forEach(planet -> asteroid.Gravity(planet));
            }
        }
        
        objects.forEach(SimulationObject::moveObject);
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

        asteroid1.draw(offscreenGraphics);

        getPlanets().forEach(planet -> planet.draw(offscreenGraphics));

        // draws white ring around the destination planet
        getPlanets().stream()
                .filter(planet -> planet.getName().equals(targetPlanet))
                .findFirst()
                .ifPresent(planet -> {
                    int r = planet.size / 2 + 8;
                    offscreenGraphics.setColor(Color.WHITE);
                    offscreenGraphics.drawOval((int) (planet.coordinates[0] - r), (int) (planet.coordinates[1] - r), 2 * r, 2 * r);
                });

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
