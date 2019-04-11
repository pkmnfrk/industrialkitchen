package com.mike_caron.industrialkitchen.proxy;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.industrialkitchen.block.ModBlocks;
import com.mike_caron.industrialkitchen.client.renderer.model.DynamicFluidModelLoader;
import com.mike_caron.industrialkitchen.item.ModItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy
    extends CommonProxy
{

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);

        OBJLoader.INSTANCE.addDomain(IndustrialKitchen.modId);
        ModelLoaderRegistry.registerLoader(new DynamicFluidModelLoader());

    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModBlocks.initModels();
        ModItems.initModels();
    }
}
