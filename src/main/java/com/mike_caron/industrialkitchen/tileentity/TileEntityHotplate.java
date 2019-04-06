package com.mike_caron.industrialkitchen.tileentity;

import com.mike_caron.mikesmodslib.block.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public class TileEntityHotplate
    extends TileEntityBase
{
    EnumTool tool;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        tool = EnumTool.NONE;

        if(compound.hasKey("tool"))
        {
            tool = EnumTool.valueOf(compound.getString("tool"));
        }
    }

    public EnumTool getTool()
    {
        return tool;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound ret = super.writeToNBT(compound);

        ret.setString("tool", tool.getName());

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
