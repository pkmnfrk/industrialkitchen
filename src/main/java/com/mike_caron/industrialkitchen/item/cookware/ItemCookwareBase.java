package com.mike_caron.industrialkitchen.item.cookware;

import com.mike_caron.industrialkitchen.heat.*;
import com.mike_caron.mikesmodslib.item.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public abstract class ItemCookwareBase
    extends ItemBase
{
    private final int capacity;
    private final int mass;
    private final PhysicsMaterial material;

    public ItemCookwareBase(String name, int capacity, int mass, PhysicsMaterial material)
    {
        super(name);

        this.capacity = capacity;
        this.mass = mass;
        this.material = material;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ItemCookwareHandler(stack, capacity, mass, material);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        IFluidHandlerItem fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        IHeatSink heatSink = stack.getCapability(CapabilityHeatSink.CAPABILITY, null);
        IHeatSink fluidHeatSink = stack.getCapability(CapabilityHeatSink.CAPABILITY, EnumFacing.UP);

        boolean got = false;
        if(fluidHandler != null)
        {
            FluidStack fluid = fluidHandler.getTankProperties()[0].getContents();

            if(fluid != null)
            {
                String tt = "Fluid: " + fluid.getLocalizedName() + " (" + fluid.amount + " mb)";

                if(fluidHeatSink != null)
                {
                    tt += " (" + formatTemperature(fluidHeatSink.getTemperature()) + ")";
                }

                tooltip.add(tt);
                got = true;
            }
        }

        if(!got)
        {
            tooltip.add("Fluid: Empty");
        }

        got = false;

        if(heatSink != null)
        {
            tooltip.add("Temperature: " + formatTemperature(heatSink.getTemperature()));
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(worldIn.isRemote)
            return;
        
        IHeatSink heatSink = stack.getCapability(CapabilityHeatSink.CAPABILITY, null);
        IHeatSink fluidHeatSink = stack.getCapability(CapabilityHeatSink.CAPABILITY, EnumFacing.UP);

        if(heatSink != null)
        {
            if(fluidHeatSink != null)
            {
                MaterialHeatSink.equalize(heatSink, fluidHeatSink);
            }

            MaterialHeatSink.equalize(heatSink, AirHeatSink.INSTANCE);
        }

        if(fluidHeatSink != null)
        {
            MaterialHeatSink.equalize(fluidHeatSink, AirHeatSink.INSTANCE);
        }

    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        if(slotChanged) return true;
        return false;
        //return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    private static String formatTemperature(double temp)
    {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(1);
        return format.format(temp - DefaultTemperature.ZERO_CELCIUS) + "°C";
    }

}
