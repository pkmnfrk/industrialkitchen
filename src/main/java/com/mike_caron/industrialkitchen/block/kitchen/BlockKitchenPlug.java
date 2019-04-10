package com.mike_caron.industrialkitchen.block.kitchen;

import com.mike_caron.industrialkitchen.tileentity.kitchen.TileEntityKitchenPlug;
import com.mike_caron.mikesmodslib.util.MultiblockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockKitchenPlug
    extends BlockKitchenBase
{
    public static final String ID = "kitchen_plug";

    public BlockKitchenPlug()
    {
        super(ID);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state)
    {
        return new TileEntityKitchenPlug();
    }

    protected TileEntityKitchenPlug getTileEntity(@Nonnull World world, @Nonnull BlockPos pos)
    {
        TileEntity ret = world.getTileEntity(pos);
        if(ret instanceof TileEntityKitchenPlug)
            return (TileEntityKitchenPlug)ret;

        return null;
    }

    public static BlockPos findClosest(@Nonnull World world, @Nonnull BlockPos start)
    {
        return MultiblockUtil.walkMultiblock(world, start, BlockKitchenBase.class, (block, pos) -> block.getBlock() instanceof BlockKitchenPlug);
    }


}
