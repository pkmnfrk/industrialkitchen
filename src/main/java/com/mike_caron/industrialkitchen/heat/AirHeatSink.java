package com.mike_caron.industrialkitchen.heat;

public class AirHeatSink
    extends ConstantHeatSink
{
    public static final AirHeatSink INSTANCE = new AirHeatSink();

    public AirHeatSink()
    {
        super(MaterialHeatSink.ROOM_TEMPERATURE);
    }

    @Override
    public double conductance()
    {
        return PhysicsMaterial.AIR.conductance;
    }

    @Override
    public void setTemperature(double temperature)
    {
    }

    @Override
    public double energyForTempChange(double delta)
    {
        return 10000000;
    }
}
