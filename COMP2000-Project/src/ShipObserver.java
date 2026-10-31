public interface ShipObserver {
    void onFuelChanged(int fuel, int maxFuel);
    void onCrash(String planetName);
}
