package Algorithms;

public class CrashMovement implements Movement {
    public void move (int[] coordinates, double[] velocity, double[] parentVelocity) {
        //add the parent object's velocity to this object
        coordinates[0] += parentVelocity[0];
        coordinates[1] += parentVelocity[1];
    }
}
