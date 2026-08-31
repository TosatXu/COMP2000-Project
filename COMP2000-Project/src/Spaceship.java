import java.lang.Math;

public class Spaceship extends SimulationObject {
    int fuel;
    float force;
    double angle;

    public Spaceship(float mass, int size, int x, int y, int fuel, float force, double angle) {
        super(mass, size, x, y);
        this.fuel = fuel;
        this.force = force;
        this.angle = angle;
    }

    public void Fly () {
        if (fuel > 0) {
            Accelerate();
            fuel--;
        }
        coordinates[0] += velocity[0];
        coordinates[1] += velocity[1];
    }

    public void Accelerate () {
        velocity[0] += (force * (float)Math.sin(angle))/mass;
        velocity[1] += (force * (float)Math.cos(angle))/mass;
    }
}