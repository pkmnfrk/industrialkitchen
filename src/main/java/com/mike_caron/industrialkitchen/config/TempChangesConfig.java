package com.mike_caron.industrialkitchen.config;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.industrialkitchen.fluid.ModFluids;
import com.mike_caron.industrialkitchen.fluid.PhysicsFluid;
import com.mike_caron.industrialkitchen.heat.PhysicsMaterial;
import net.minecraft.util.ResourceLocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TempChangesConfig
    extends ConfigBase
{
    private TempChangesConfig(){}



    public static void loadConfig(Document document)
    {
        NodeList fluids = document.getDocumentElement().getElementsByTagName("fluid");

        for(int i = 0; i < fluids.getLength(); i++)
        {
            loadFluid((Element)fluids.item(i));
        }

        NodeList items = document.getDocumentElement().getElementsByTagName("item");

        for(int i = 0; i < items.getLength(); i++)
        {
            loadItem((Element)items.item(i));
        }

    }

    private static void loadItem(Element itemEl)
    {

    }

    private static void loadFluid(Element fluidEl)
    {
        String name = fluidEl.getAttribute("id");

        PhysicsFluid fluid;

        fluid = new PhysicsFluid(name,
            new ResourceLocation(IndustrialKitchen.modId, "fluids/fluid_still"),
            new ResourceLocation(IndustrialKitchen.modId, "fluids/fluid_flow"),
            false
        );

        PhysicsMaterial.Builder mat = PhysicsMaterial.builder(PhysicsMaterial.WATER);

        colorAttribute(fluidEl, "color").ifPresent(fluid::setColor);
        intAttribute(fluidEl, "alpha").ifPresent(fluid::setAlpha);
        intAttribute(fluidEl, "density").ifPresent(fluid::setDensity);
        intAttribute(fluidEl, "luminosity").ifPresent(fluid::setLuminosity);
        intAttribute(fluidEl, "temperature").ifPresent(fluid::setTemperature);
        intAttribute(fluidEl, "viscosity").ifPresent(fluid::setViscosity);
        doubleAttribute(fluidEl, "specificHeat").ifPresent(mat::setSpecificHeat);
        doubleAttribute(fluidEl, "conductivity").ifPresent(mat::setConductance);

        fluid.setPhysicsMaterial(mat.build());

        ModFluids.fluids.put(name, fluid);

    }


}
