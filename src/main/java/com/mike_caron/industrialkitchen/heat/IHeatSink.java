package com.mike_caron.industrialkitchen.heat;

import org.cyclops.commoncapabilities.api.capability.temperature.ITemperature;

public interface IHeatSink
    extends ITemperature
{
    void addEnergy(double energy);
    double energyForTempChange(double delta);
    double conductance();
}
