package com.mike_caron.industrialkitchen.config;

import com.mike_caron.industrialkitchen.recipes.FluidStackPromise;
import com.mike_caron.industrialkitchen.util.FluidStackUtils;
import com.mike_caron.mikesmodslib.util.ItemUtils;
import com.mike_caron.mikesmodslib.util.StringUtil;
import net.minecraft.item.ItemStack;
import org.w3c.dom.Element;

import java.util.Optional;

public class ConfigBase
{
    protected static Optional<Float> floatAttribute(Element el, String name)
    {
        if(el.hasAttribute(name))
        {
            return Optional.of(Float.parseFloat(el.getAttribute(name)));
        }
        return Optional.empty();
    }

    protected static Optional<Integer> intAttribute(Element el, String name)
    {
        if(el.hasAttribute(name))
        {
            return Optional.of(Integer.parseInt(el.getAttribute(name)));
        }
        return Optional.empty();
    }

    protected static Optional<Integer> colorAttribute(Element el, String name)
    {
        if(el.hasAttribute(name))
        {
            return Optional.of(StringUtil.parseColorString(el.getAttribute(name)));
        }
        return Optional.empty();
    }

    protected static Optional<Double> doubleAttribute(Element el, String name)
    {
        if(el.hasAttribute(name))
        {
            return Optional.of(Double.parseDouble(el.getAttribute(name)));
        }
        return Optional.empty();
    }

    protected static Optional<ItemStack> itemStackAttribute(Element el, String name)
    {
        if(el.hasAttribute(name))
        {
            String txt = el.getAttribute(name);
            return Optional.of(ItemUtils.getStackFromTag(txt));
        }
        return Optional.empty();
    }

    protected static Optional<FluidStackPromise> fluidStackPromiseAttribute(Element el, String name)
    {
        if(el.hasAttribute(name))
        {
            String txt = el.getAttribute(name);
            return Optional.of(FluidStackUtils.getFluidPromiseFromTag(txt));
        }
        return Optional.empty();
    }
}
