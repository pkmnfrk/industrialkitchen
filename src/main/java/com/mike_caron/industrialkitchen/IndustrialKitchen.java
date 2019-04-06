package com.mike_caron.industrialkitchen;

import com.mike_caron.industrialkitchen.integrations.MainCompatHandler;
import com.mike_caron.industrialkitchen.proxy.IModProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Mod(
        modid = IndustrialKitchen.modId,
        name = IndustrialKitchen.name,
        version = IndustrialKitchen.version,
        acceptedMinecraftVersions = "[1.12.2]",
        dependencies = "required-after:mikesmodslib@[1.0.1,)"
)
@Mod.EventBusSubscriber
public class IndustrialKitchen
{
    public static final String modId = "industrialkitchen";
    public static final String name = "Industrial Kitchen";
    public static final String version = "0.1.0";

    public static final Logger logger = LogManager.getLogger(modId);

    public static final CreativeTab creativeTab = new CreativeTab();

    @SuppressWarnings("unused")
    @Mod.Instance(modId)
    public static IndustrialKitchen instance;

    @SidedProxy(
            serverSide = "com.mike_caron.industrialkitchen.proxy.CommonProxy",
            clientSide = "com.mike_caron.industrialkitchen.proxy.ClientProxy"
    )
    public static IModProxy proxy;

    @Mod.EventHandler
    public  void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);

        MainCompatHandler.registerAll();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent evt)
    {
    }

    @Mod.EventHandler
    public void serverUnload(FMLServerStoppingEvent evt)
    {
    }

    public static void debugLog(String message, Object... params)
    {
        logger.debug(message, params);
    }

}
