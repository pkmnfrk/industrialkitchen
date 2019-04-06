package com.mike_caron.industrialkitchen.tileentity;

import com.mike_caron.industrialkitchen.item.ModItems;
import com.mike_caron.mikesmodslib.block.TileEntityBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class TileEntityHotplate
    extends TileEntityBase
{
    ItemStack tool;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        tool = null;

        if(compound.hasKey("tool"))
        {
            tool = new ItemStack(compound.getCompoundTag("tool"));
        }

        if(world != null && world.isRemote)
        {
            markAndNotify();
        }
    }

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
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound ret = super.writeToNBT(compound);

        if(tool != null)
        {
            ret.setTag("tool", tool.serializeNBT());
        }

        return ret;
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
