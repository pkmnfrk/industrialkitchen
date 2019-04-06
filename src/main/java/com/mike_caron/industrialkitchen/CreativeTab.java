package com.mike_caron.industrialkitchen;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTab extends CreativeTabs
{
    public CreativeTab()
    {
        super(IndustrialKitchen.modId);
    }

    @Override
    public ItemStack createIcon()
    {
        return null;
        // FIXME: this -v
        //return new ItemStack(ModItems.efficiencyCatalyst, 1);
    }


}
