package Algorithms;

public class ChildMovement implements Movement {
    public void move (int[] coordinates, double[] velocity, double[] parentVelocity) {
        //Change the position using the velocity
        coordinates[0] += velocity[0];
        coordinates[1] += velocity[1];
        //add the parent object's velocity to this object
        coordinates[0] += parentVelocity[0];
        coordinates[1] += parentVelocity[1];
    }
}
