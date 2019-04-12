package com.mike_caron.industrialkitchen.fluid;

import com.mike_caron.industrialkitchen.heat.PhysicsMaterial;
import com.mike_caron.mikesmodslib.fluid.FluidBase;
import net.minecraft.util.ResourceLocation;

public class PhysicsFluid
    extends FluidBase
{
    PhysicsMaterial material;

    public PhysicsFluid(String fluidName, ResourceLocation still, ResourceLocation flowing)
    {
        super(fluidName, still, flowing);
    }

    public PhysicsFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, boolean autoRegister)
    {
        super(fluidName, still, flowing, autoRegister);
    }

    public PhysicsMaterial getPhysicsMaterial()
    {
        return material;
    }

    public void setPhysicsMaterial(PhysicsMaterial material)
    {
        this.material = material;
    }
}
