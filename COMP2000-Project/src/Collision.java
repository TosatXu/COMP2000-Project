public class Collision {
    public static boolean checkCollision(SimulationObject obj1, SimulationObject obj2) {
        double distance = obj1.CalculateDistance(obj1.coordinates, obj2.coordinates);
        double radiusSum = (obj1.size / 2.0) + (obj2.size / 2.0);
        return distance <= radiusSum;
    }
}
