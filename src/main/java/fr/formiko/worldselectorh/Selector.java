package fr.formiko.worldselectorh;

import java.util.function.BiConsumer;

public class Selector {
    private final int xMin;
    private final int zMin;
    private final int xMax;
    private final int zMax;
    private long processedBlocks = 0l;
    private final int BLOCKS_PER_COLUMN = 384;
    public Selector(int x1, int z1, int x2, int z2) {
        xMin = Math.min(x1, x2);
        zMin = Math.min(z1, z2);
        xMax = Math.max(x1, x2);
        zMax = Math.max(z1, z2);
    }

    public void executeColumn(BiConsumer<Integer, Integer> consumer) {
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                consumer.accept(x, z);
                processedBlocks += BLOCKS_PER_COLUMN;
            }
        }
    }
    public void executeBlock(TriConsumer<Integer, Integer, Integer> consumer) {
        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {
                for (int y = 0; y < 256; y++) {
                    consumer.accept(x, y, z);
                    processedBlocks++;
                }
            }
        }
    }
    public long getColumnsCount() { return (xMax - xMin + 1l) * (zMax - zMin + 1l); }
    public long getBlocksCount() { return getColumnsCount() * BLOCKS_PER_COLUMN; }
    public double progress() { return (double) processedBlocks / (double) getBlocksCount(); }
}
