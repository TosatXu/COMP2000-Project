import java.awt.Color;

// mass, size, x, y, fuel, force, angle, launchForce

// balanced spaceship
class Rocket extends Spaceship {
    public Rocket (int x, int y) {
        super(500f, 12, x, y, 1000, 10f, 45.0, 4000f);
        colour = Color.RED;
    }
}

// light and fast, but less fuel spaceship
class Fighter extends Spaceship {
    public Fighter (int x, int y) {
        super(300f, 10, x, y, 600, 15f, 45.0, 4000f);
        colour = Color.ORANGE;
    }
}

// heavy and slow, but more fuel spaceship
class Cargo extends Spaceship {
    public Cargo (int x, int y) {
        super(700f, 16, x, y, 1800, 8f, 45.0, 4000f);
        colour = Color.GREEN;
    }
}