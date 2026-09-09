import Algorithms.*;

public class SimulationObject {
    public float mass;
    public int size, x, y;
    public int[] coordinates;
    double[] velocity;
    Movement movement;

    public SimulationObject(float mass, int size, int x, int y){
        this.mass = mass;
        this.size = size;
        coordinates = new int[]{x, y};
        velocity = new double[]{0, 0};
    }

    public void moveObject () {
        movement.move(coordinates, velocity);
    }

    public void changeMovement (Movement m) {
        movement = m;
    }

    public void Gravity (SimulationObject target) {
        double G = 6.674 * 0.00000000001; //Gravitational constant

        double R = CalculateDistance(this.coordinates, target.coordinates);

        double F = G * ((this.mass * target.mass)/(R*R)); //Calculate gravitational force between this object and target object
        
        double A = 0f;
        try {
            A = CalculateAngle(this.coordinates, target.coordinates);
        }
        catch (ArithmeticException e) {
            A = 90f;
            System.out.println(e);
        }

        //System.out.println(Math.toDegrees(A));

        Accelerate(F, A);
    }

    public double CalculateDistance (int[] coordinate1, int[] coordinate2) {
        double x = Math.abs(coordinate1[0] - coordinate2[0]);
        double y = Math.abs(coordinate1[1] - coordinate2[1]);

        return Math.sqrt(x*x + y*y);
    }

    public double CalculateAngle (int[] coordinate1, int[] coordinate2) throws ArithmeticException {
        double x = -(double)((coordinate1[0]) - (coordinate2[0]));
        double y = -(double)((coordinate1[1]) - (coordinate2[1]));

        return -Math.atan2(y, x);
    }

    public void Accelerate (double f, double angle) {
        //Calculate x and y axis of acceleration using the angle of movement and applying the acceleration to the velocity
        velocity[0] += (f * Math.cos(angle))/mass;
        velocity[1] += -(f * Math.sin(angle))/mass;
    }
}