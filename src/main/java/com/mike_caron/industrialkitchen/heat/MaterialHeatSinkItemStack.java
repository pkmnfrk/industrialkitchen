package com.mike_caron.industrialkitchen.heat;

import com.mike_caron.mikesmodslib.util.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MaterialHeatSinkItemStack
    extends MaterialHeatSink
{
    public static final String ENERGY_NBT_KEY = "HeatJoules";

    protected ItemStack container;

    public MaterialHeatSinkItemStack(ItemStack container, PhysicsMaterial material, int mass)
    {
        super(material, mass);

        this.container = container;
    }

    public ItemStack getContainer()
    {
        return container;
    }

    @Override
    public double getEnergy()
    {
        NBTTagCompound tag = ItemUtils.getItemTag(container);
        if(tag.hasKey(ENERGY_NBT_KEY))
        {
            return tag.getDouble(ENERGY_NBT_KEY);
        }

        return getMaterial().energy(getMass(), ROOM_TEMPERATURE);
    }

    @Override
    public void setEnergy(double energy)
    {
        NBTTagCompound tag = ItemUtils.getItemTag(container);
        tag.setDouble(ENERGY_NBT_KEY, energy);
    }
}
