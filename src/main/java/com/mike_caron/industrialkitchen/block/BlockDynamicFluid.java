package com.mike_caron.industrialkitchen.block;

import com.mike_caron.mikesmodslib.block.BlockFluidBase;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;

public class BlockDynamicFluid
    extends BlockFluidBase

{
    public BlockDynamicFluid(Fluid fluid, String name)
    {
        super(fluid, name, MapColor.BLUE);
    }

    @Override
    public void render()
    {
        //ModelLoader.setCustomStateMapper(this, new DynamicFluidStateMap(fluidName));
        if(fluidName.equals("generic_fluid"))
        {
            ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
        }
        else
        {
            ModelLoader.setCustomStateMapper(this, new StateMap.Builder().withSuffix("/fluid")/*.ignore(LEVEL)*/.build());
        }
    }
}
