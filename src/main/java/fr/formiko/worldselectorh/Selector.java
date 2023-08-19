package fr.formiko.worldselectorh;

import java.util.UUID;
import java.util.function.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class Selector {
    private final int xMin;
    private final int zMin;
    private final int xMax;
    private final int zMax;
    private long processedBlocks = 0l;
    private final int BLOCKS_PER_COLUMN = 384;
    private final UUID worldUUID;
    public Selector(int x1, int z1, int x2, int z2, UUID worldUUID) {
        xMin = Math.min(x1, x2);
        zMin = Math.min(z1, z2);
        xMax = Math.max(x1, x2);
        zMax = Math.max(z1, z2);
        this.worldUUID = worldUUID;
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
    public UUID getWorldUUID() { return worldUUID; }
    public World getWorld() { return Bukkit.getWorld(worldUUID); }
}
