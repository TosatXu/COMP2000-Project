package Algorithms;

public class ShipMovement implements Movement {
    public void move (int[] coordinates, double[] velocity) {
        //Change the position using the velocity
        coordinates[0] += velocity[0];
        coordinates[1] += velocity[1];
    }
}
