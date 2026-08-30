public class SimulationObject {
    public float mass;
    public int size, x, y;
    public int[] coordinates;
    float[] velocity;

    public SimulationObject(float mass, int size, int x, int y){
        this.mass = mass;
        this.size = size;
        coordinates = new int[]{x, y};
        velocity = new float[]{0, 0};
    }
}