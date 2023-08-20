package fr.formiko.worldselectorh;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class Selector {
    private final int xMin;
    private final int zMin;
    private final int yMin = -64;
    private final int xMax;
    private final int zMax;
    private Vector3 currentBlock;
    private long processedBlocks = 0l;
    private final int BLOCKS_PER_COLUMN = 384;
    private final UUID worldUUID;

    public Selector(int x1, int z1, int x2, int z2, UUID worldUUID) {
        xMin = Math.min(x1, x2);
        zMin = Math.min(z1, z2);
        xMax = Math.max(x1, x2);
        zMax = Math.max(z1, z2);
        this.worldUUID = worldUUID;
        currentBlock = new Vector3(xMin, yMin - 1, zMin);
    }

    public long getColumnsCount() { return (xMax - xMin + 1l) * (zMax - zMin + 1l); }
    public long getBlocksCount() { return getColumnsCount() * BLOCKS_PER_COLUMN; }
    public double progress() { return (double) processedBlocks / (double) getBlocksCount(); }
    public UUID getWorldUUID() { return worldUUID; }
    public World getWorld() { return Bukkit.getWorld(worldUUID); }


    public boolean hasNextBlock() { return processedBlocks < getBlocksCount(); }
    public boolean hasNextColumn() { return processedBlocks < getBlocksCount(); }
    public Vector3 nextBlock() {
        processedBlocks++;
        currentBlock.setY(currentBlock.getY() + 1);
        if (currentBlock.getY() > BLOCKS_PER_COLUMN) {
            currentBlock.setY(-64);
            currentBlock.setZ(currentBlock.getZ() + 1);
            if (currentBlock.getZ() > zMax) {
                currentBlock.setZ(zMin);
                currentBlock.setX(currentBlock.getX() + 1);
                if (currentBlock.getX() > xMax) {
                    return null;
                }
            }
        }
        return currentBlock;
    }
}
