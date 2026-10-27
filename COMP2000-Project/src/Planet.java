import Algorithms.*;

public class Planet extends SimulationObject {
    private String name;

    public Planet (String name, float mass, int size, int x, int y){
        super(mass, size, x, y);
        movement = new NoMovement();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}