public class App {
    public static void main(String[] args) throws Exception {
        Spaceship ship = new Spaceship(10f, 0, 0, 0, 10, 5, 45f);

        int n = 0;
        while (n < 100) {
            ship.Fly();
            System.out.println(ship.coordinates[0] + ", " + ship.coordinates[1]);
            System.out.println(ship.angle);
            n++;
        }
    }
}
