package com.mike_caron.industrialkitchen.recipes;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidStackPromise
{
    String name;
    public int amount;

    public FluidStackPromise(String name, int amount)
    {
        this.name = name;
        this.amount = amount;

    }

    public FluidStack resolve()
    {
        return FluidRegistry.getFluidStack(name, amount);
    }
}
