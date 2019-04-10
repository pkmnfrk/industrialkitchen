package com.mike_caron.industrialkitchen.block.kitchen;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import java.util.List;

public class BlockKitchenOven
    extends BlockKitchenBase
{
    public static final String ID = "kitchen_oven";

    public BlockKitchenOven()
    {
        super(ID);
    }

    @Override
    protected void addAdditionalPropeties(List<IProperty<?>> properties)
    {
        super.addAdditionalPropeties(properties);

        //properties.add(Props.IS_LIT);
    }

    @Override
    protected IBlockState addStateProperties(IBlockState blockState)
    {
        return super.addStateProperties(blockState)
            //.withProperty(Props.IS_LIT, false)
            ;
    }
}
