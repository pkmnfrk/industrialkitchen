package com.mike_caron.industrialkitchen.item.cookware;

import com.mike_caron.mikesmodslib.item.ItemBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemPan
    extends ItemBase
{
    public static final String ID = "frying_pan";

    public ItemPan()
    {
        super(ID);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new Handler(stack);
    }

    static class Handler
        implements ICapabilityProvider
    {
        FluidHandlerItemStack fluidHandler;

        public Handler(ItemStack stack)
        {
            fluidHandler = new FluidHandlerItemStack(stack, 1000);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
        {
            if(fluidHandler.hasCapability(capability, facing))
                return true;

            return false;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(fluidHandler.hasCapability(capability, facing))
            {
                return fluidHandler.getCapability(capability, facing);
            }

            return null;
        }
    }
}
