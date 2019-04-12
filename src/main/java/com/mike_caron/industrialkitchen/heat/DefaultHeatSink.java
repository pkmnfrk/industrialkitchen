package com.mike_caron.industrialkitchen.heat;

import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature;

public class DefaultHeatSink
    extends ConstantHeatSink
{
    public DefaultHeatSink()
    {
        super(DefaultTemperature.ZERO_CELCIUS);
    }
}
