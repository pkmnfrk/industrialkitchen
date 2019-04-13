package com.mike_caron.industrialkitchen.heat;

public class ConstantHeatSink
    implements IHeatSink
{
    private double temperature;

    public ConstantHeatSink(double temp)
    {
        this.temperature = temp;
    }

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    @Override
    public void addEnergy(double energy)
    {

    }

    @Override
    public double energyForTempChange(double delta)
    {
        return 1000000 * Math.signum(delta);
    }

    @Override
    public double conductance()
    {
        return 1;
    }

    @Override
    public double getTemperature()
    {
        return temperature;
    }

    @Override
    public double getMaximumTemperature()
    {
        return temperature;
    }

    @Override
    public double getMinimumTemperature()
    {
        return temperature;
    }

    @Override
    public double getDefaultTemperature()
    {
        return temperature;
    }

    @Override
    public double excessEnergy()
    {
        return 0;
    }
}
