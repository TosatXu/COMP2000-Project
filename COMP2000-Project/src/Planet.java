import Algorithms.*;

public class Planet extends SimulationObject {
    public Planet (float mass, int size, int x, int y){
        super(mass, size, x, y);
        movement = new NoMovement();
    }
}