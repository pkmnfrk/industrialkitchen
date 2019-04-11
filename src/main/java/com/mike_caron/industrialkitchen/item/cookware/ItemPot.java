package com.mike_caron.industrialkitchen.item.cookware;

import com.mike_caron.mikesmodslib.item.ItemBase;
import com.mike_caron.mikesmodslib.util.ItemUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemPot
    extends ItemBase
{
    public static final String ID = "pot";

    public ItemPot()
    {
        super(ID);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new Handler(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        NBTTagCompound nbt = ItemUtils.getItemTag(stack);
        if(nbt.hasKey(FluidHandlerItemStack.FLUID_NBT_KEY))
        {
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
            tooltip.add("Fluid: " + fluid.getLocalizedName() + " (" + fluid.amount + " mb)");
        }
    }

    static class Handler
        implements ICapabilityProvider
    {
        FluidHandlerItemStack fluidHandler;

        public Handler(ItemStack stack)
        {
            fluidHandler = new FluidHandlerItemStack(stack, 4000);
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
