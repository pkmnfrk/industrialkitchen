package com.mike_caron.industrialkitchen.heat;

public class PhysicsMaterial
{
    public static final PhysicsMaterial COPPER = new PhysicsMaterial(0.386, 0.9);
    public static final PhysicsMaterial IRON = new PhysicsMaterial(0.450, 0.7);
    public static final PhysicsMaterial WATER = new PhysicsMaterial(4.186, 0.6);
    public static final PhysicsMaterial AIR = new PhysicsMaterial(1000, 0.02);

    public final double specificHeat;
    public final double conductance;

    public PhysicsMaterial(double specificHeat, double conductance)
    {
        this.specificHeat = specificHeat;
        this.conductance = conductance;
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

        public Builder(PhysicsMaterial base)
        {
            this.specificHeat = base.specificHeat;
            this.conductance = base.conductance;
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

        public PhysicsMaterial build()
        {
            return new PhysicsMaterial(specificHeat, conductance);
        }
    }


}
