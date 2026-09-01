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
            Accelerate(force, this.angle);
            fuel--;
        }
    }

    public void AdjustAngle () {
        //Adjust the angle of movement based on the direction of travel
        this.angle = Math.atan2(velocity[1], velocity[0]);
        System.out.println("This angle: " + Math.toDegrees(this.angle));
        System.out.println(velocity[0] + ", " + velocity[1]);
    }
}