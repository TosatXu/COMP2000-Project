public interface ShipState {
    void update(Spaceship ship);
}

class ReadyState implements ShipState {
    public void update(Spaceship ship) {
        ship.Launch(ship.launchForce);
        ship.setState(new FlyingState());
    }
}

class FlyingState implements ShipState {
    public void update(Spaceship ship) {
        if (ship.isThrusting) {
            if (ship.fuel > 0) {
                ship.Accelerate(ship.force, ship.angle);
                ship.fuel--;
                ship.notifyFuelChanged();
            } else {
                ship.isThrusting = false;
            }
        }
    }
}

class CrashedState implements ShipState {
    public void update(Spaceship ship) {
    }
}