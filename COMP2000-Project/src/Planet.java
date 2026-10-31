import Algorithms.*;
import java.awt.*;

public class Planet extends SimulationObject {
    private String name;
    private Color colour;

    public Planet (String name, float mass, int size, int x, int y, Color colour) {
        super(mass, size, x, y);
        movement = new NoMovement();
        this.name = name;
        this.colour = colour;
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