import Algorithms.*;
import java.awt.*;

public class Planet extends SimulationObject {
    private String name;
    private Color colour;
    float orbitalRadius;

    public Planet (String name, float mass, int size, int x, int y, Color colour, float orbitalRadius) {
        super(mass, size, x, y);
        movement = new NoMovement();
        this.name = name;
        this.colour = colour;
        this.orbitalRadius = orbitalRadius;
    }

    public Planet (String name, float mass, int size, int x, int y, Color colour, SimulationObject parent, float orbitalRadius) {
        super(mass, size, x, y, parent);
        movement = new ChildMovement();
        this.name = name;
        this.colour = colour;
        this.orbitalRadius = orbitalRadius;
    }

    public void orbit () {
        double v = (2 * Math.PI * orbitalRadius)/100;
        double angle = CalculateAngle(this.coordinates, parent.coordinates) + Math.toRadians(90);
        velocity[0] = v * Math.cos(angle) * -1;
        velocity[1] = v * Math.sin(angle);
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