package com.mike_caron.industrialkitchen.item;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.mikesmodslib.item.ItemBase;
import com.mike_caron.mikesmodslib.util.MappedModelLoader;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(IndustrialKitchen.modId)
public class ModItems
{
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();


    }

    public static void registerEvents()
    {

    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        MappedModelLoader.Builder models = MappedModelLoader.builder();
        try
        {
            for (Field field : ModItems.class.getDeclaredFields())
            {
                if (Modifier.isStatic(field.getModifiers()) && ItemBase.class.isAssignableFrom(field.getType()))
                {
                    ItemBase item = (ItemBase) field.get(null);

                    item.initModel();
                }

                if (Modifier.isStatic(field.getModifiers()) && field.getType().isArray() && ItemBase.class.isAssignableFrom(field.getType().getComponentType()))
                {
                    ItemBase[] items = (ItemBase[]) field.get(null);

                    if(items == null) continue;

                    for(ItemBase item : items)
                    {
                        if(item != null)
                        {
                            item.initModel();
                        }
                    }
                }
            }
        }
        catch(IllegalAccessException ex)
        {
            throw new RuntimeException("Unable to reflect upon myelf??");
        }
        //soulboundTalisman.initModel();

        ModelLoaderRegistry.registerLoader(models.build(IndustrialKitchen.modId));
    }
}
