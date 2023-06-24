package dev.serenity.event.impl;

import dev.serenity.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BlockBBEvent extends Event {
    private BlockPos blockPos;
    private Block block;
    private AxisAlignedBB boundingBox;
    private int x;
    private int y;
    private int z;

    public BlockBBEvent(BlockPos blockPos, Block block, AxisAlignedBB boundingBox) {
        this.blockPos = blockPos;
        this.block = block;
        this.boundingBox = boundingBox;
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public Block getBlock() {
        return block;
    }

    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}

