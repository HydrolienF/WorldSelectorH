package fr.formiko.worldselectorh;

public class Selector {
    private final int xMin;
    private final int zMin;
    private final int xMax;
    private final int zMax;
    public Selector(int x1, int z1, int x2, int z2) {
        xMin = Math.min(x1, x2);
        zMin = Math.min(z1, z2);
        xMax = Math.max(x1, x2);
        zMax = Math.max(z1, z2);
    }
}
