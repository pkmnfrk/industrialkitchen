package com.mike_caron.industrialkitchen.heat;

public class AirHeatSink
    implements IHeatSink
{
    public static final AirHeatSink INSTANCE = new AirHeatSink();

    @Override
    public void addEnergy(double energy)
    {

    }

    @Override
    public double energyForTempChange(double delta)
    {
        //basically infinite
        return 10000000;
    }

    @Override
    public double conductance()
    {
        return PhysicsMaterial.AIR.conductance;
    }

    @Override
    public double getTemperature()
    {
        return MaterialHeatSink.ROOM_TEMPERATURE;
    }

    @Override
    public double getMaximumTemperature()
    {
        return MaterialHeatSink.ROOM_TEMPERATURE;
    }

    @Override
    public double getMinimumTemperature()
    {
        return MaterialHeatSink.ROOM_TEMPERATURE;
    }

    @Override
    public double getDefaultTemperature()
    {
        return MaterialHeatSink.ROOM_TEMPERATURE;
    }
}
