import java.lang.Math;

public class Spaceship extends SimulationObject {
    int fuel;
    float force;
    double angle;

    public Spaceship(float mass, int size, int x, int y, int fuel, float force, double angle) {
        super(mass, size, x, y);
        this.fuel = fuel;
        this.force = force;
        this.angle = Math.toRadians(angle);
    }

    public void Fly () {
        if (fuel > 0) {
            Accelerate();
            fuel--;
        }

        //Change the position using the velocity
        coordinates[0] += velocity[0];
        coordinates[1] += velocity[1];
    }

    public void Accelerate () {
        //Calculate x and y axis of acceleration using the angle of movement and applying the acceleration to the velocity
        velocity[0] += (force * (float)Math.sin(angle))/mass;
        velocity[1] += (force * (float)Math.cos(angle))/mass;

        //Adjust the angle of movement based on the direction of travel
        angle = Math.atan2(velocity[0], velocity[1]);
    }
}