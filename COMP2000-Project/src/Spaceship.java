import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import Algorithms.ShipMovement;
import Algorithms.NoMovement;

public class Spaceship extends SimulationObject implements Collision {
    int fuel;
    int maxFuel;
    float force;
    double angle;
    float launchForce;

    public boolean isThrusting = false;

    private ShipState state;
    private List<ShipObserver> observers = new ArrayList<>();

    public Spaceship(float mass, int size, int x, int y, int fuel, float force, double angle, float launchForce) {
        super(mass, size, x, y);
        this.fuel = fuel;
        this.maxFuel = fuel;

        this.force = force;
        this.angle = Math.toRadians(angle);
        this.launchForce = launchForce;
        movement = new ShipMovement();
        state = new ReadyState();
    }

    public void Fly () {
        state.update(this);
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public boolean isCrashed() {
        return state instanceof CrashedState;
    }

    public void addObserver(ShipObserver observer) {
        observers.add(observer);
        observer.onFuelChanged(fuel, maxFuel);
    }
 
    public void notifyFuelChanged() {
        for (ShipObserver observer : observers) {
            observer.onFuelChanged(fuel, maxFuel);
        }
    }
 
    public void crash(String planetName) {
        if (isCrashed()) {
            return;
        }
        setState(new CrashedState());
        changeMovement(new NoMovement());
        isThrusting = false;
        observers.forEach(observer -> observer.onCrash(planetName));
    }

    public void AdjustAngle () {
        //Adjust the angle of movement based on the direction of travel
        this.angle = -Math.atan2(velocity[1], velocity[0]);
        //System.out.println("This angle: " + Math.toDegrees(this.angle));
        //System.out.println(velocity[0] + ", " + velocity[1]);
    }

    public int getFuel() {
        return fuel;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public void Launch (float launchForce) {
        Accelerate(launchForce, this.angle);
    }

    public boolean checkCollision(SimulationObject obj1, SimulationObject obj2) {
        double distance = obj1.CalculateDistance(obj1.coordinates, obj2.coordinates);
        double radiusSum = (obj1.size / 2.0) + (obj2.size / 2.0);
        return distance <= radiusSum;
    }

    public void setAngle(double angle) throws InvalidSettingException {
        if (angle < 0 || angle > 90) {
            throw new InvalidSettingException("Invalid angle");
        }
        this.angle = Math.toRadians(angle);
    }
 
    public void setEngineForce(float force) throws InvalidSettingException {
        if (force < 1 || force > 100) {
            throw new InvalidSettingException("Invalid engine force");
        }
        this.force = force;
    }
 
    public void setLaunchForce(float launchForce) throws InvalidSettingException {
        if (launchForce < 1000 || launchForce > 10000) {
            throw new InvalidSettingException("Invalid launch force");
        }
        this.launchForce = launchForce;
    }
}