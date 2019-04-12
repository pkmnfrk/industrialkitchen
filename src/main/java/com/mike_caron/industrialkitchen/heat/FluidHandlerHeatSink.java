package com.mike_caron.industrialkitchen.heat;

import com.mike_caron.mikesmodslib.util.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidHandlerHeatSink
    extends MaterialHeatSink
    implements IFluidHandlerItem
{
    protected int capacity;
    protected ItemStack container;
    protected String key;

    public FluidHandlerHeatSink(ItemStack container, int capacity)
    {
        this(container, capacity, FluidHandlerItemStack.FLUID_NBT_KEY);
    }

    public FluidHandlerHeatSink(ItemStack container, int capacity, String key)
    {
        this.container = container;
        this.capacity = capacity;
        this.key = key;
    }

    @Nonnull
    @Override
    public ItemStack getContainer()
    {
        return container;
    }

    @Override
    public IFluidTankProperties[] getTankProperties()
    {
        return new IFluidTankProperties[] {
            new FluidTankProperties(getFluid(), capacity)
        };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        if(resource == null)
            return 0;

        FluidStack curStack = getFluid();
        int amount = 0;

        if(curStack == null || curStack.getFluid() == resource.getFluid())
        {
            amount = Math.min(resource.amount, capacity);

            if(curStack != null)
            {
                amount = Math.min(amount, capacity - curStack.amount);
            }
        }

        if(doFill)
        {
            FluidStack newStack = new FluidStack(resource.getFluid(), amount);
            setFluid(newStack);

            PhysicsMaterial mat = getMaterial();
            if(curStack == null)
            {
                setEnergy(mat.energy(massForFluid(newStack), getDefaultTempForFluid(newStack.getFluid())));
            }
            else
            {
                addEnergy(mat.energy(massForFluid(newStack.getFluid(), newStack.amount - curStack.amount), getDefaultTempForFluid(newStack.getFluid())));
            }
        }

        return amount;
    }

    @Nullable
    @Override
    public FluidStack drain(@Nonnull FluidStack resource, boolean doDrain)
    {
        FluidStack curStack = getFluid();

        if(curStack == null || curStack.getFluid() != resource.getFluid())
            return null;

        return drainInternal(resource.amount, doDrain, curStack);
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        FluidStack curStack = getFluid();

        if(curStack == null)
            return null;

        return drainInternal(maxDrain, doDrain, curStack);
    }

    @Nullable
    private FluidStack drainInternal(int maxDrain, boolean doDrain, @Nonnull FluidStack curStack)
    {
        int amt = Math.min(curStack.amount, maxDrain);

        if(amt <= 0)
            return null;

        FluidStack ret = new FluidStack(curStack.getFluid(), amt);
        if(doDrain)
        {
            //...
            //PhysicsMaterial mat = getMaterial();
            FluidStack newFluid = new FluidStack(ret.getFluid(), curStack.amount - amt);

            if(newFluid.amount == 0)
            {
                newFluid = null;
            }

            setFluid(newFluid);

            //addEnergy(mat.energy(massForFluid(curStack.getFluid(), amt), getTemperature()));
        }

        return ret;
    }


    @Override
    public PhysicsMaterial getMaterial()
    {
        FluidStack stack = getFluid();
        if(stack == null)
        {
            return PhysicsMaterial.AIR;
        }
        else
        {
            return getMaterialForFluid(stack.getFluid());
        }

    }

    @Override
    public void setMaterial(PhysicsMaterial material)
    {
    }

    @Override
    public double getMass()
    {
        FluidStack stack = getFluid();
        if(stack == null)
        {
            return 1000;
        }
        return massForFluid(stack);
    }

    @Override
    public void setMass(double newMass)
    {
    }

    @Override
    public double getEnergy()
    {
        NBTTagCompound nbt = ItemUtils.getItemTag(container);

        if(nbt.hasKey(key))
        {
            NBTTagCompound fluid = nbt.getCompoundTag(key);
            if(!fluid.hasKey("Energy"))
            {
                //throw new IllegalStateException("Missing Energy key?!");
                return 0;
            }
            return fluid.getDouble("Energy");
        }

        return PhysicsMaterial.AIR.energy(1000, ROOM_TEMPERATURE);
    }

    @Override
    public void setEnergy(double energy)
    {
        NBTTagCompound nbt = ItemUtils.getItemTag(container);
        if(nbt.hasKey(key))
        {
            NBTTagCompound fluid = nbt.getCompoundTag(key);

            fluid.setDouble("Energy", energy);
        }
    }

    @Nullable
    public FluidStack getFluid()
    {
        NBTTagCompound nbt = ItemUtils.getItemTag(container);
        if(nbt.hasKey(key))
        {
            return FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(key));
        }

        return null;
    }

    public void setFluid(@Nullable FluidStack fluid)
    {
        NBTTagCompound nbt = ItemUtils.getItemTag(container);
        if(fluid == null && nbt.hasKey(key))
        {
            nbt.removeTag(key);
        }
        else if(fluid != null)
        {
            PhysicsMaterial mat = getMaterialForFluid(fluid.getFluid());

            double energy = mat.energy(massForFluid(fluid), getTemperature());
            NBTTagCompound tag = new NBTTagCompound();
            fluid.writeToNBT(tag);

            nbt.setTag(key, tag);

            setEnergy(energy);
        }
    }

    protected double massForFluid(FluidStack fluidStack)
    {
        return massForFluid(fluidStack.getFluid(), fluidStack.amount);
    }

    protected double massForFluid(Fluid fluid, int amount)
    {
        return amount * fluid.getDensity() / 1000.0;
    }
}
