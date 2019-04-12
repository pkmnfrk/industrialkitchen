package com.mike_caron.industrialkitchen.heat;

import com.mike_caron.industrialkitchen.fluid.PhysicsFluid;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature;

public class MaterialHeatSink
    implements IHeatSink, INBTSerializable<NBTBase>
{
    public static final double ROOM_TEMPERATURE = DefaultTemperature.ZERO_CELCIUS + 20;
    public static final double MIN_TEMPERATURE = DefaultTemperature.ZERO_CELCIUS - 20;
    public static final double MAX_TEMPERATURE = DefaultTemperature.ZERO_CELCIUS + 300;

    private double mass;
    private PhysicsMaterial material;
    protected double energy;

    protected MaterialHeatSink() {}

    public MaterialHeatSink(PhysicsMaterial material, int mass)
    {
        this(material, mass, ROOM_TEMPERATURE);
    }

    public MaterialHeatSink(PhysicsMaterial material, int mass, double initialTemp)
    {
        this.mass = mass;
        this.material = material;
        this.energy = material.energy(mass, initialTemp);
    }

    @Override
    public double getTemperature()
    {
        return getMaterial().temperature(getMass(), getEnergy());
    }

    @Override
    public double getMaximumTemperature()
    {
        return DefaultTemperature.ZERO_CELCIUS;
    }

    @Override
    public double getMinimumTemperature()
    {
        return MIN_TEMPERATURE;
    }

    @Override
    public double getDefaultTemperature()
    {
        return MAX_TEMPERATURE;
    }

    public void addEnergy(double energy)
    {
        this.setEnergy(this.getEnergy() + energy);
    }

    @Override
    public double energyForTempChange(double delta)
    {
        return getMaterial().energy(getMass(), delta);
    }

    @Override
    public double conductance()
    {
        return getMaterial().conductance;
    }

    public static void equalize(IHeatSink a, IHeatSink b)
    {
        //to bring everything into equilibrium, our temp needs to match their temp

        double aTemp = a.getTemperature();
        double bTemp = b.getTemperature();
        double aDelta = (bTemp - aTemp) / 2;

        aDelta *= Math.min(a.conductance(), b.conductance());

        if(Math.abs(aDelta) < 0.01) //eh, close enough
            return;

        double bDelta = -aDelta;

        double aEnergy = a.energyForTempChange(aDelta) * 0.05;
        double bEnergy = b.energyForTempChange(bDelta) * 0.05;

        if(Math.abs(aEnergy) < Math.abs(bEnergy))
        {
            a.addEnergy(aEnergy);
            b.addEnergy(-aEnergy);
        }
        else
        {
            a.addEnergy(-bEnergy);
            b.addEnergy(bEnergy);
        }
    }

    public double getEnergy()
    {
        return energy;
    }

    public void setEnergy(double energy)
    {
        this.energy = energy;
    }

    @Override
    public NBTBase serializeNBT()
    {
        return new NBTTagDouble(getEnergy());
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        setEnergy(0);
        if(nbt instanceof NBTTagDouble)
        {
            setEnergy(((NBTTagDouble) nbt).getDouble());
        }
    }

    public double getMass()
    {
        return mass;
    }

    public void setMass(double newMass)
    {
        double temp = getMaterial().temperature(getMass(), getEnergy());
        this.mass = newMass;
        setEnergy(getMaterial().energy(getMass(), temp));
    }

    public static PhysicsMaterial getMaterialForFluid(Fluid fluid)
    {
        if(fluid instanceof PhysicsFluid)
        {
            return ((PhysicsFluid) fluid).getPhysicsMaterial();
        }

        return PhysicsMaterial.WATER;
    }

    public static double getDefaultTempForFluid(Fluid fluid)
    {
        if(fluid == FluidRegistry.WATER)
            return MaterialHeatSink.ROOM_TEMPERATURE;

        return fluid.getTemperature();
    }

    public PhysicsMaterial getMaterial()
    {
        return material;
    }

    public void setMaterial(PhysicsMaterial material)
    {
        this.material = material;
    }
}
