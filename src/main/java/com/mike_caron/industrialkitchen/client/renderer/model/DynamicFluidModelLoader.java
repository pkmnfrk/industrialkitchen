package com.mike_caron.industrialkitchen.client.renderer.model;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.industrialkitchen.fluid.ModFluids;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelFluid;

import javax.annotation.Nonnull;

public class DynamicFluidModelLoader
    implements ICustomModelLoader
{
    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager)
    {

    }

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation)
    {
        if(modelLocation instanceof ModelResourceLocation)
        {
            ModelResourceLocation res = (ModelResourceLocation)modelLocation;

            if(res.getNamespace().equals(IndustrialKitchen.modId) && res.getPath().endsWith("/fluid"))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    @Nonnull
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) throws Exception
    {
        ModelResourceLocation res = (ModelResourceLocation)modelLocation;

        String fluid = res.getPath().substring(0, res.getPath().length() - 6);

        //return ModelFluid.WATER;
        return new ModelFluid(ModFluids.fluids.get(fluid));
    }
}
