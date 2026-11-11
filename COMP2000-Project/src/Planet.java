import Algorithms.*;
import java.awt.*;

public class Planet extends SimulationObject {
    private Color colour;
    double orbitalRadius;
    double orbitPeriod;

    public Planet (String name, float mass, int size, int x, int y, Color colour) {
        super(mass, size, x, y);
        movement = new NoMovement();
        this.name = name;
        this.colour = colour;
        orbitPeriod = 0;
        orbitalRadius = 0;
    }

    public Planet (String name, float mass, int size, int x, int y, Color colour, SimulationObject parent, double orbitPeriod) {
        super(mass, size, x, y, parent);
        movement = new ChildMovement();
        this.name = name;
        this.colour = colour;
        this.orbitPeriod = orbitPeriod;
        orbitalRadius = CalculateDistance(coordinates, parent.coordinates);
    }

    public void orbit () {
        //System.out.println(coordinates[0] + ", " + coordinates[1]);
        if (orbitPeriod > 0) {
            double v = (2 * Math.PI * orbitalRadius)/orbitPeriod;
            double angle = CalculateAngle(this.coordinates, parent.coordinates) + Math.toRadians(90.5);
            velocity[0] = v * Math.cos(angle) * -1;
            velocity[1] = v * Math.sin(angle);
        }
    }

    public String getName() {
        return name;
    }

    public void draw(Graphics g) {

    int planetX = coordinates[0] - size / 2;
    int planetY = coordinates[1] - size / 2;

    g.setColor(colour);
    g.fillOval(planetX, planetY, size, size);

    g.setColor(colour.brighter());
    g.fillOval(
            planetX + size / 5,
            planetY + size / 5,
            size / 3,
            size / 3);

    g.setColor(Color.WHITE);
    g.drawString(name, planetX, planetY - 5);
    }

}