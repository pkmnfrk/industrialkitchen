package com.mike_caron.industrialkitchen.util;

import com.mike_caron.industrialkitchen.recipes.FluidStackPromise;
import com.mike_caron.mikesmodslib.util.ItemUtils;

import java.util.regex.Matcher;

public class FluidStackUtils
{
    private FluidStackUtils() {}

    public static FluidStackPromise getFluidPromiseFromTag(String tag)
    {
        try
        {
            int count = 1000;

            Matcher res = ItemUtils.leadingCount.matcher(tag);
            while(res.find())
            {
                count = Integer.parseInt(res.group(1));
                tag = tag.substring(res.end());
            }

            return new FluidStackPromise(tag, count);
        }
        catch (NumberFormatException | NullPointerException ex)
        {
            //handled below
        }

        //return null;
        throw new RuntimeException("I don't understand the fluid " + tag);
    }
}
