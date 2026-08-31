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

    public void moveObject () {
        //Change the position using the velocity
        coordinates[0] += velocity[0];
        coordinates[1] += velocity[1];
    }

    public void Gravity (SimulationObject target) {
        double G = 6.674 * 0.00001; //Gravitational constant

        double R = CalculateDistance(this.coordinates, target.coordinates);

        double F = G * ((this.mass * target.mass)/(R*R)); //Calculate gravitational force between this object and target object

        double A = CalculateAngle(this.coordinates, target.coordinates);

        System.out.println(Math.toDegrees(A));

        Accelerate(F, A);
    }

    public double CalculateDistance (int[] coordinate1, int[] coordinate2) {
        double x = Math.abs(coordinate1[0] - coordinate2[0]);
        double y = Math.abs(coordinate1[1] - coordinate2[1]);

        return Math.sqrt(x*x + y*y);
    }

    public double CalculateAngle (int[] coordinate1, int[] coordinate2) {
        double x = (double)((coordinate1[0]-400) - (coordinate2[0]-400));
        double y = (double)((coordinate1[1]-300) - (coordinate2[1])-300);

        if (x != 0) {
            return Math.atan(y/x);
        }
        else if (y > 0) {
            return Math.toRadians(90);
        }
        return Math.toRadians(-90);
    }

    public void Accelerate (double f, double angle) {
        //Calculate x and y axis of acceleration using the angle of movement and applying the acceleration to the velocity
        velocity[0] += (f * (float)Math.sin(angle))/mass;
        velocity[1] += (f * (float)Math.cos(angle))/mass;
    }
}