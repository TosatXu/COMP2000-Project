public class Planet extends SimulationObject {
    public int gravityStrength;

    public Planet (float mass, int size, int x, int y, int gravityStrength){
        super(mass, size, x, y);
        this.gravityStrength = gravityStrength;
    }
}