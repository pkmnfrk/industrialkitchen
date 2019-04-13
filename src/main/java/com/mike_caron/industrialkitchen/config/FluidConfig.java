package com.mike_caron.industrialkitchen.config;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.industrialkitchen.fluid.ModFluids;
import com.mike_caron.industrialkitchen.fluid.PhysicsFluid;
import com.mike_caron.industrialkitchen.heat.PhysicsMaterial;
import com.mike_caron.industrialkitchen.recipes.FluidStackPromise;
import com.mike_caron.industrialkitchen.recipes.TemperatureRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
        if(handleSpecialFluid(fluidEl))
            return;

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

        handleRecipes(fluid, fluidEl);

    }

    private static boolean handleSpecialFluid(Element fluidEl)
    {
        String id = fluidEl.getAttribute("id");
        if(id.equals("lava") || id.equals("water"))
        {
            Fluid fluid = null;

            if(id.equals("lava"))
                fluid = FluidRegistry.LAVA;

            if(id.equals("water"))
                fluid = FluidRegistry.WATER;

            handleRecipes(fluid, fluidEl);

            return true;
        }
        return false;
    }

    private static void handleRecipes(Fluid fluid, Element fluidEl)
    {
        NodeList children = fluidEl.getChildNodes();

        for(int i = 0; i < children.getLength(); i++)
        {
            Node n = children.item(i);

            if(n instanceof Element)
            {
                Element el = (Element)n;
                if(el.getTagName().equals("freezes") || el.getTagName().equals("boils"))
                {
                    handleTempChange(fluid, el);
                }
            }
        }

    }

    private static void handleTempChange(Fluid fluid, Element el)
    {
        TemperatureRecipe.Builder builder = new TemperatureRecipe.Builder();

        double energyMult = 1;

        if(el.getTagName().equals("freezes"))
            energyMult = -1;

        builder.withEnergy(energyMult * Double.parseDouble(el.getAttribute("energy")));

        FluidStackPromise inputStack = new FluidStackPromise(fluid.getName(), 1000);

        intAttribute(el, "amount").ifPresent(amt -> inputStack.amount = amt);

        itemStackAttribute(el, "item").ifPresent(builder::withOutputItem);
        fluidStackPromiseAttribute(el, "fluid").ifPresent(builder::withOutputFluid);

        builder.withInputFluid(inputStack);

        try
        {
            TemperatureRecipe recipe = builder.build();


        }
        catch(Exception ex)
        {
            IndustrialKitchen.logger.error("Error loading recipe " + fluid.getName() + " -> " + el.getTagName() + ": " + ex.getMessage());
        }


    }


}
