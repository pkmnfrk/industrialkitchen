package com.mike_caron.industrialkitchen.tileentity;

import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenBase;
import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenPlug;
import com.mike_caron.mikesmodslib.block.TileEntityBase;
import com.mike_caron.mikesmodslib.energy.SerializableEnergyStorage;
import com.mike_caron.mikesmodslib.util.MultiblockUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityKitchenPlug
    extends TileEntityBase
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

    public static BlockPos findClosest(@Nonnull World world, @Nonnull BlockPos start)
    {
        return MultiblockUtil.findClosest(world, start, BlockKitchenBase.class, block -> block.getBlock() instanceof BlockKitchenPlug);
    }


}
