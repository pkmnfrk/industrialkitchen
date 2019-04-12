package com.mike_caron.industrialkitchen.item.cookware;

import com.mike_caron.industrialkitchen.heat.PhysicsMaterial;

public class ItemPot
    extends ItemCookwareBase
{
    public static final String ID = "pot";

    public ItemPot()
    {
        super(ID, 4000, 1000, PhysicsMaterial.IRON);
    }
}
