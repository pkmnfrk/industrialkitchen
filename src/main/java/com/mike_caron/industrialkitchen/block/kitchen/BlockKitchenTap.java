package com.mike_caron.industrialkitchen.block.kitchen;

import com.mike_caron.mikesmodslib.util.MultiblockUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockKitchenTap
    extends BlockKitchenBase
{
    public static final String ID = "kitchen_tap";

    public BlockKitchenTap()
    {
        super(ID);
    }

    public static BlockPos findClosest(@Nonnull World world, @Nonnull BlockPos start)
    {
        return MultiblockUtil.findClosest(world, start, BlockKitchenBase.class, block -> block.getBlock() instanceof BlockKitchenTap);
    }
}
