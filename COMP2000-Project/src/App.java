public class App {
    public static void main(String[] args) throws Exception {
        Spaceship ship = new Spaceship(15f, 0, -50, 0, 20, 5, 45f);
        Planet earth = new Planet(100000f, 0, 0, 0);

        int n = 0;
        while (n < 100) {
            ship.Fly();
            ship.Gravity(earth);
            ship.AdjustAngle();
            ship.moveObject();
            System.out.println(ship.coordinates[0] + ", " + ship.coordinates[1]);
            //System.out.println(ship.angle);
            n++;
        }
    }
}
