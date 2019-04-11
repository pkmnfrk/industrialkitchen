package com.mike_caron.industrialkitchen.config;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.industrialkitchen.fluid.ModFluids;
import com.mike_caron.mikesmodslib.fluid.FluidBase;
import net.minecraft.util.ResourceLocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FluidConfig
    extends ConfigBase
{
    private FluidConfig(){}



    public static void loadConfig(Document document)
    {
        NodeList fluids = document.getDocumentElement().getElementsByTagName("fluid");

        for(int i = 0; i < fluids.getLength(); i++)
        {
            loadFluid((Element)fluids.item(i));
        }

    }

    private static void loadFluid(Element fluidEl)
    {
        String name = fluidEl.getAttribute("id");

        FluidBase fluid;

        fluid = new FluidBase(name,
            new ResourceLocation(IndustrialKitchen.modId, "fluids/fluid_still"),
            new ResourceLocation(IndustrialKitchen.modId, "fluids/fluid_flow"),
            false
        );


        colorAttribute(fluidEl, "color").ifPresent(fluid::setColor);
        intAttribute(fluidEl, "alpha").ifPresent(fluid::setAlpha);
        intAttribute(fluidEl, "density").ifPresent(fluid::setDensity);
        intAttribute(fluidEl, "luminosity").ifPresent(fluid::setLuminosity);
        intAttribute(fluidEl, "temperature").ifPresent(fluid::setTemperature);
        intAttribute(fluidEl, "viscosity").ifPresent(fluid::setViscosity);

        ModFluids.fluids.put(name, fluid);

    }


}
