import Algorithms.*;
import java.awt.*;

public class Asteroid extends SimulationObject {
    private Color color;
    
    public Asteroid(float mass, int size, int x, int y, double vx, double vy, Color color){
        super(mass, size, x, y);
        this.color = color;
        this.velocity[0] = vx;
        this.velocity[1] = vy;

        this.movement = new ShipMovement();
    }

    public void draw (Graphics g) {
        int asteroidX = coordinates[0] - size/2;
        int asteroidY = coordinates[1] - size/2;

        g.setColor(color);
        g.fillOval(asteroidX, asteroidY, size, size);

        g.setColor(Color.DARK_GRAY);
        g.fillOval(asteroidX + size/4, asteroidY + size/4, size/3, size/3);
    }
    
}
