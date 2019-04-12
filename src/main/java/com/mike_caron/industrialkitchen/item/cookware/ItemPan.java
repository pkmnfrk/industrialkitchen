package com.mike_caron.industrialkitchen.item.cookware;

import com.mike_caron.industrialkitchen.heat.PhysicsMaterial;

public class ItemPan
    extends ItemCookwareBase
{
    public static final String ID = "frying_pan";

    public ItemPan()
    {
        super(ID, 1000, 500, PhysicsMaterial.IRON);
    }

}
