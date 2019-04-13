package com.mike_caron.industrialkitchen.heat;

import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature;

public class PhysicsMaterial
{
    public static final PhysicsMaterial COPPER = new PhysicsMaterial(0.386, 0.9, DefaultTemperature.ZERO_CELCIUS, DefaultTemperature.ZERO_CELCIUS + 100);
    public static final PhysicsMaterial IRON = new PhysicsMaterial(0.450, 0.7);
    public static final PhysicsMaterial WATER = new PhysicsMaterial(4.186, 0.6);
    public static final PhysicsMaterial AIR = new PhysicsMaterial(1000, 0.02);

    public final double specificHeat;
    public final double conductance;
    public final double minTemperature;
    public final double maxTemperature;

    public PhysicsMaterial(double specificHeat, double conductance)
    {
        this(specificHeat, conductance, 0, 5000);
    }

    public PhysicsMaterial(double specificHeat, double conductance, double minTemperature, double maxTemperature)
    {
        this.specificHeat = specificHeat;
        this.conductance = conductance;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public double energy(double mass, double temperature)
    {
        return mass * specificHeat * temperature;
    }

    public double temperature(double mass, double energy)
    {
        return energy / (specificHeat * mass);
    }

    public static Builder builder(PhysicsMaterial base)
    {
        return new Builder(base);
    }

    public static class Builder
    {
        double specificHeat;
        double conductance;
        double minTemp;
        double maxTemp;

        public Builder(PhysicsMaterial base)
        {
            this.specificHeat = base.specificHeat;
            this.conductance = base.conductance;
            this.minTemp = base.minTemperature;
            this.maxTemp = base.maxTemperature;
        }

        public Builder setSpecificHeat(double specificHeat)
        {
            this.specificHeat = specificHeat;
            return this;
        }

        public Builder setConductance(double conductance)
        {
            this.conductance = conductance;
            return this;
        }

        public Builder setMinTemperature(double minTemp)
        {
            this.minTemp = minTemp;
            return this;
        }

        public Builder setMaxTemperature(double maxTemp)
        {
            this.maxTemp = maxTemp;
            return this;
        }

        public PhysicsMaterial build()
        {
            return new PhysicsMaterial(specificHeat, conductance, minTemp, maxTemp);
        }
    }


}
