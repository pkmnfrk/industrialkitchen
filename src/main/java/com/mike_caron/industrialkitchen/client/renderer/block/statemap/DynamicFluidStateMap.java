package com.mike_caron.industrialkitchen.client.renderer.block.statemap;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

import javax.annotation.Nonnull;
import java.util.Map;

public class DynamicFluidStateMap
    extends StateMapperBase
{
    private String name;

    public DynamicFluidStateMap(String name)
    {
        this.name = name;
    }

    @Override
    @Nonnull
    protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
    {
        String variant = getPropertyString(state.getProperties());
        return new ModelResourceLocation(IndustrialKitchen.modId + ":fluid/" + name , variant);
    }

    @Override
    public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn)
    {
        return super.putStateModelLocations(blockIn);
    }
}
