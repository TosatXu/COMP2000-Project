import java.lang.Math;

import Algorithms.ShipMovement;

public class Spaceship extends SimulationObject implements Collision {
    int fuel;
    float force;
    double angle;
    boolean hasLaunched;
    float launchForce;

    public boolean isThrusting = false;

    public Spaceship(float mass, int size, int x, int y, int fuel, float force, double angle, float launchForce) {
        super(mass, size, x, y);
        this.fuel = fuel;
        this.force = force;
        this.angle = Math.toRadians(angle);
        hasLaunched = false;
        this.launchForce = launchForce;
        movement = new ShipMovement();
    }

    public void Fly () {
        if (!hasLaunched) {
            Launch(launchForce);
            hasLaunched = true;
        }

        if (isThrusting == true){
            if (fuel > 0){
                Accelerate(force, this.angle);
                fuel--;

            } else {
                isThrusting = false;
            }
        }
    }

    public void AdjustAngle () {
        //Adjust the angle of movement based on the direction of travel
        this.angle = -Math.atan2(velocity[1], velocity[0]);
        //System.out.println("This angle: " + Math.toDegrees(this.angle));
        //System.out.println(velocity[0] + ", " + velocity[1]);
    }

    public void Launch (float launchForce) {
        Accelerate(launchForce, this.angle);
    }

    public boolean checkCollision(SimulationObject obj1, SimulationObject obj2) {
        double distance = obj1.CalculateDistance(obj1.coordinates, obj2.coordinates);
        double radiusSum = (obj1.size / 2.0) + (obj2.size / 2.0);
        return distance <= radiusSum;
    }

    public void setAngle(double angle) {
        this.angle = Math.toRadians(angle);
    }

    public void setEngineForce(float force) {
        this.force = force;
    }
}