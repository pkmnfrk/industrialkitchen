package com.mike_caron.industrialkitchen.heat;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import org.cyclops.cyclopscore.modcompat.capabilities.DefaultCapabilityStorage;

public class CapabilityHeatSink
{
    @CapabilityInject(IHeatSink.class)
    public static Capability<IHeatSink> CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IHeatSink.class, new DefaultCapabilityStorage<IHeatSink>(), DefaultHeatSink::new);
    }
}
