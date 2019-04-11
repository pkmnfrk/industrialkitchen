package com.mike_caron.industrialkitchen;

import com.mike_caron.industrialkitchen.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTab extends CreativeTabs
{
    public CreativeTab()
    {
        super(IndustrialKitchen.modId);
    }

    @Override
    @Nonnull
    public ItemStack createIcon()
    {
        return new ItemStack(ModBlocks.kitchenBlank, 1);
    }


}
