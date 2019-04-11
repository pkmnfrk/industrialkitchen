package com.mike_caron.industrialkitchen.fluid;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.mikesmodslib.fluid.FluidBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class ModFluids
{
    public static ResourceLocation STILL = new ResourceLocation(IndustrialKitchen.modId, "fluids/fluid_still");
    public static ResourceLocation FLOWING = new ResourceLocation(IndustrialKitchen.modId, "fluids/fluid_flow");

    public static final HashMap<String, FluidBase> fluids = new HashMap<>();


    public static FluidBase generic_fluid = (FluidBase)new FluidBase("generic_fluid", STILL, FLOWING, false)
        .setColor(new Color(255, 255, 255));

    public static void register()
    {
        FluidRegistry.registerFluid(generic_fluid);
        //no bucket for generic fluid

        for(Map.Entry<String, FluidBase> v : fluids.entrySet())
        {
            FluidRegistry.registerFluid(v.getValue());
            FluidRegistry.addBucketForFluid(v.getValue());
        }
    }
}
