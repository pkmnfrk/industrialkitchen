package com.mike_caron.industrialkitchen.block.kitchen;

import com.mike_caron.industrialkitchen.tileentity.TileEntityKitchenPlug;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
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
}
