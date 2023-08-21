package fr.formiko.worldselectorh;

import java.io.Serializable;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Selector implements Serializable {
    private final int xMin;
    private final int zMin;
    private final int yMin = -64;
    private final int yMax = 320;
    private final int xMax;
    private final int zMax;
    private Vector3 currentBlock;
    private long processedBlocks = 0l;
    private final int BLOCKS_PER_COLUMN = 384;
    private final int BLOCK_PER_CHUNK = BLOCKS_PER_COLUMN * 16 * 16;
    private final UUID worldUUID;

    public Selector(int x1, int z1, int x2, int z2, UUID worldUUID) {
        xMin = Math.min(x1, x2);
        zMin = Math.min(z1, z2);
        xMax = Math.max(x1, x2);
        zMax = Math.max(z1, z2);
        this.worldUUID = worldUUID;
        currentBlock = new Vector3(xMin, yMin, zMin);
    }

    public long getColumnsCount() { return (xMax - xMin + 1l) * (zMax - zMin + 1l); }
    public long getBlocksCount() { return getColumnsCount() * BLOCKS_PER_COLUMN; }
    public double progress() { return (double) processedBlocks / (double) getBlocksCount(); }
    public UUID getWorldUUID() { return worldUUID; }
    public World getWorld() { return Bukkit.getWorld(worldUUID); }


    public boolean hasNextBlock() { return processedBlocks < getBlocksCount(); }
    public boolean hasNextColumn() { return processedBlocks < getBlocksCount(); }
    public Block nextBlock() {
        Block b = getWorld().getBlockAt(currentBlock.getX(), currentBlock.getY(), currentBlock.getZ());
        processedBlocks++;
        currentBlock.setY(currentBlock.getY() + 1);
        if (currentBlock.getY() > yMax) {
            currentBlock.setY(-64);
            currentBlock.setZ(currentBlock.getZ() + 1);
            if (currentBlock.getZ() > zMax) {
                currentBlock.setZ(zMin);
                currentBlock.setX(currentBlock.getX() + 1);
                // if (currentBlock.getX() > xMax) {
                // return null;
                // }
            }
        }
        System.out.println(currentBlock.getX() + " " + currentBlock.getY() + " " + currentBlock.getZ());
        return b;
    }
    public Block nextColumn() {
        Block b = getWorld().getBlockAt(currentBlock.getX(), currentBlock.getY(), currentBlock.getZ());
        processedBlocks += BLOCKS_PER_COLUMN;
        currentBlock.setY(-64);
        currentBlock.setZ(currentBlock.getZ() + 1);
        if (currentBlock.getZ() > zMax) {
            currentBlock.setZ(zMin);
            currentBlock.setX(currentBlock.getX() + 1);
            // if (currentBlock.getX() > xMax) {
            // return null;
            // }
        }
        return b;
    }
    public Chunk nextChunk() {
        Chunk c = getWorld().getChunkAt(currentBlock.getX() / 16, currentBlock.getZ() / 16);
        processedBlocks += BLOCK_PER_CHUNK;
        currentBlock.setZ(currentBlock.getZ() + 16);
        if (currentBlock.getZ() - (currentBlock.getZ() % 16) > zMax) {
            currentBlock.setZ(zMin);
            currentBlock.setX(currentBlock.getX() + 16);
            // if (currentBlock.getX() - (currentBlock.getX() % 16) > xMax) {
            // return null;
            // }
        }
        return c;
    }
}
