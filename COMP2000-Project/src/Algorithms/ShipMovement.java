package Algorithms;

public class ShipMovement implements Movement {
    public void move (int[] coordinates, double[] velocity) {
        //Change the position using the velocity
        coordinates[0] += velocity[0];
        coordinates[1] += velocity[1];

        if (coordinates[0] < -10) {
            coordinates[0] = 900;
        }
        if (coordinates[0] > 910) {
            coordinates[0] = 0;
        }
        if (coordinates[1] < -10) {
            coordinates[1] = 900;
        }
        if (coordinates[1] > 910) {
            coordinates[1] = 0;
        }
    }
}
