or.RED);
        int shipCenterX = (int) ship.coordinates[0];
        int shipCenterY = (int) ship.coordinates[1];
        int shipHalf = ship.size/2;
        int[]shipXPoints = { shipCenterX, shipCenterX - shipHalf, shipCenterX + shipHalf };
        int[]shipYPoints = { shipCenterY - shipHalf, shipCenterY + shipHalf, shipCenterY + shipHalf };

        g.fillPolygon(sh