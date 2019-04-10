package com.mike_caron.industrialkitchen.tileentity.appliance;

import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenPlug;
import com.mike_caron.industrialkitchen.item.ModItems;
import com.mike_caron.industrialkitchen.tileentity.kitchen.TileEntityKitchenPlug;
import com.mike_caron.mikesmodslib.util.TileEntityProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityHotplate
    extends TileEntityAppliance implements ITickable
{
    ItemStack tool;

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
            TileEntityHotplate.this.markAndNotify();
        }
    };

    public EnumTool getTool()
    {
        if(tool == null)
            return EnumTool.NONE;

        if(tool.getItem() == ModItems.pan)
            return EnumTool.PAN;

        return EnumTool.NONE;
    }

    @Nonnull
    public ItemStack insertTool(@Nonnull ItemStack itemStack)
    {
        if(tool != null && !itemStack.isEmpty())
        {
            return itemStack;
        }

        ItemStack ret = tool;

        IBlockState oldState = world.getBlockState(pos);

        if(itemStack.isEmpty())
        {
            tool = null;
        }
        else
        {
            tool = itemStack;
        }

        IBlockState newState = world.getBlockState(pos);

        markAndNotify(oldState, newState);

        if(ret == null)
            ret = ItemStack.EMPTY;

        return ret;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        tool = null;

        if(compound.hasKey("tool"))
        {
            tool = new ItemStack(compound.getCompoundTag("tool"));
        }

        if(compound.hasKey("plug"))
        {
            plug.deserializeNBT(compound.getCompoundTag("plug"));
        }

        if(world != null && world.isRemote)
        {
            markAndNotify();
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound ret = super.writeToNBT(compound);

        if(tool != null)
        {
            ret.setTag("tool", tool.serializeNBT());
        }

        ret.setTag("plug", plug.serializeNBT());

        return ret;
    }

    public boolean isValid()
    {
        return plug.isValid();
    }

    @Override
    public void update()
    {
        //TileEntityKitchenPlug plugEntity = plug.getTileEntity(world);

    }

    @Override
    public void refreshConnections()
    {
        plug.invalidate();
    }

    public enum EnumTool
        implements IStringSerializable
    {
        NONE("none"),
        PAN("pan"),
        POT("pot");

        private String name;

        EnumTool(String name)
        {
            this.name = name;
        }

        @Override
        @Nonnull
        public String getName()
        {
            return name;
        }
    }
}
