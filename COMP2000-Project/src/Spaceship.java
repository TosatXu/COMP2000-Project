public class Spaceship extends SimulationObject {
    int fuel;
    float force;

    public Spaceship(int mass, int size, int x, int y, int fuel, float force) {
        super(mass, size, x, y);
        this.fuel = fuel;
        this.force = force;
    }
}