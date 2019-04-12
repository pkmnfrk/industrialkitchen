package com.mike_caron.industrialkitchen.item.cookware;

import com.mike_caron.industrialkitchen.heat.CapabilityHeatSink;
import com.mike_caron.industrialkitchen.heat.FluidHandlerHeatSink;
import com.mike_caron.industrialkitchen.heat.MaterialHeatSinkItemStack;
import com.mike_caron.industrialkitchen.heat.PhysicsMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

class ItemCookwareHandler
    implements ICapabilityProvider
{
    FluidHandlerHeatSink fluidHandler;
    MaterialHeatSinkItemStack heatSink;

    public ItemCookwareHandler(ItemStack stack, int capacity, int mass, PhysicsMaterial material)
    {
        fluidHandler = new FluidHandlerHeatSink(stack, capacity);
        heatSink = new MaterialHeatSinkItemStack(stack, material, mass);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY
            || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;

        if(capability == CapabilityHeatSink.CAPABILITY)
            return true;

        return false;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(fluidHandler);
        }
        else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
        }
        else if(capability == CapabilityHeatSink.CAPABILITY)
        {
            if(facing == null)
                return CapabilityHeatSink.CAPABILITY.cast(heatSink);
            else
                return CapabilityHeatSink.CAPABILITY.cast(fluidHandler);
        }

        return null;
    }
}
