package com.mike_caron.industrialkitchen.tileentity.kitchen;

import com.mike_caron.mikesmodslib.energy.SerializableEnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityKitchenPlug
    extends TileEntityKitchenBase
{
    SerializableEnergyStorage energy = new SerializableEnergyStorage(10000);

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityEnergy.ENERGY)
            return true;

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityEnergy.ENERGY)
            return CapabilityEnergy.ENERGY.cast(energy);

        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        energy.deserializeNBT(compound.getTag("energy"));
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);

        compound.setTag("energy", energy.serializeNBT());

        return compound;
    }
}
