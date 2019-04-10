package com.mike_caron.industrialkitchen.tileentity.kitchen;

import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenPlug;
import com.mike_caron.mikesmodslib.util.TileEntityProxy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityKitchenOven
    extends TileEntityKitchenBase
{
    TileEntityProxy<TileEntityKitchenPlug> plug = new TileEntityProxy<TileEntityKitchenPlug>()
    {
        @Nullable
        @Override
        protected BlockPos findTileEntity()
        {
            return BlockKitchenPlug.findClosest(world, getPos().offset(EnumFacing.DOWN));
        }

        @Override
        protected void markDirty()
        {
            markAndNotify();
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if(compound.hasKey("plug"))
        {
            plug.deserializeNBT(compound.getCompoundTag("plug"));
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);

        compound.setTag("plug", plug.serializeNBT());

        return compound;
    }

    public boolean isValid()
    {
        return plug.isValid();
    }

    @Override
    public void refreshConnections()
    {
        plug.invalidate();
    }
}
