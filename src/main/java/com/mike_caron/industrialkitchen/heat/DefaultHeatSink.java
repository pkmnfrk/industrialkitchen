package com.mike_caron.industrialkitchen.heat;

import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature;

public class DefaultHeatSink
    implements IHeatSink
{
    @Override
    public void addEnergy(double energy)
    {

    }

    @Override
    public double conductance()
    {
        return 1;
    }

    @Override
    public double energyForTempChange(double delta)
    {
        return 0;
    }

    @Override
    public double getTemperature()
    {
        return DefaultTemperature.ZERO_CELCIUS;
    }

    @Override
    public double getMaximumTemperature()
    {
        return DefaultTemperature.ZERO_CELCIUS;
    }

    @Override
    public double getMinimumTemperature()
    {
        return DefaultTemperature.ZERO_CELCIUS;
    }

    @Override
    public double getDefaultTemperature()
    {
        return DefaultTemperature.ZERO_CELCIUS;
    }
}
