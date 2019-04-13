package com.mike_caron.industrialkitchen.recipes;

import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemperatureRecipes
{
    public static final Map<Fluid, List<TemperatureRecipe>> recipes = new HashMap<>();

    public static void addRecipe(Fluid fluid, TemperatureRecipe recipe)
    {
        List<TemperatureRecipe> r;

        if(!recipes.containsKey(fluid))
        {
            recipes.put(fluid, r = new ArrayList<>());
        }
        else
        {
            r = recipes.get(fluid);
        }

        r.add(recipe);
    }
}
