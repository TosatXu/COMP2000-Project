public class SimulationObject {
    public int mass, size, x, y;
    public int[] coordinates;
    float[] velocity;

    public SimulationObject(int mass, int size, int x, int y){
        this.mass = mass;
        this.size = size;
        this.x = x;
        this.y = y;
        this.coordinates[0] = x;
        this.coordinates[1] = y;

    }
}