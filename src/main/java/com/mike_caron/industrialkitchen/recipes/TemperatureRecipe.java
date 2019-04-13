package com.mike_caron.industrialkitchen.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TemperatureRecipe
{
    double energy;

    @Nullable
    ItemStack inputItem = null;
    @Nullable
    FluidStack inputFluid = null;
    @Nullable
    ItemStack outputItem = null;
    @Nullable
    FluidStack outputFluid = null;

    @Nullable
    FluidStackPromise inputFluidPromise = null;
    @Nullable
    FluidStackPromise outputFluidPromise = null;

    public static Builder builder()
    {
        return new Builder();
    }

    public double getEnergy()
    {
        return energy;
    }

    @Nullable
    public ItemStack getInputItem()
    {
        return inputItem;
    }

    @Nullable
    public ItemStack getOutputItem()
    {
        return outputItem;
    }

    @Nullable
    public FluidStack getInputFluid()
    {
        if(inputFluid == null && inputFluidPromise != null)
        {
            inputFluid = inputFluidPromise.resolve();
            inputFluidPromise = null;
        }
        return inputFluid;
    }

    @Nullable
    public FluidStack getOutputFluid()
    {
        if(outputFluid == null && outputFluidPromise != null)
        {
            outputFluid = outputFluidPromise.resolve();
            outputFluidPromise = null;
        }
        return outputFluid;
    }

    public static class Builder
    {
        TemperatureRecipe recipe = new TemperatureRecipe();

        public Builder withInputItem(@Nonnull ItemStack itemStack)
        {
            recipe.inputItem = itemStack;
            return this;
        }

        public Builder withOutputItem(@Nonnull ItemStack itemStack)
        {
            recipe.outputItem = itemStack;
            return this;
        }

        public Builder withInputFluid(@Nonnull FluidStack fluidStack)
        {
            recipe.inputFluid = fluidStack;
            return this;
        }

        public Builder withInputFluid(@Nonnull FluidStackPromise fluidStack)
        {
            recipe.inputFluidPromise = fluidStack;
            return this;
        }

        public Builder withOutputFluid(@Nonnull FluidStack fluidStack)
        {
            recipe.outputFluid = fluidStack;
            return this;
        }

        public Builder withOutputFluid(@Nonnull FluidStackPromise fluidStack)
        {
            recipe.outputFluidPromise = fluidStack;
            return this;
        }

        public Builder withEnergy(double energy)
        {
            recipe.energy = energy;
            return this;
        }

        public TemperatureRecipe build()
            throws Exception
        {
            if(recipe.inputItem == null && recipe.inputFluid == null && recipe.inputFluidPromise == null)
                throw new Exception("No input item or fluid specified");

            if(recipe.outputItem == null && recipe.outputFluid == null && recipe.outputFluidPromise == null)
                throw new Exception("No output item or fluid specified");

            if(recipe.inputItem != null && (recipe.inputFluid != null || recipe.inputFluidPromise != null))
                throw new Exception("Both an item and a fluid input are specified");

            if(recipe.outputItem != null && (recipe.outputFluid != null || recipe.outputFluidPromise != null))
                throw new Exception("Both an item and a fluid output are specified");

            return recipe;
        }
    }
}
