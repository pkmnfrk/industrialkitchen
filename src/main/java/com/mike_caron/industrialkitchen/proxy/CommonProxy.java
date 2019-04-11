package com.mike_caron.industrialkitchen.proxy;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.industrialkitchen.config.Configs;
import com.mike_caron.industrialkitchen.fluid.ModFluids;
import com.mike_caron.industrialkitchen.item.ModItems;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

@Mod.EventBusSubscriber(Side.SERVER)
public class CommonProxy
    implements IModProxy
{

    public static File modDirectory;
    public static File configDirectory;

    //public static Configuration config;

    @SuppressWarnings("EmptyMethod")
    public void preInit(FMLPreInitializationEvent e)
    {
        //File directory = e.getModConfigurationDirectory();
        //config = new Configuration(new File(directory.getPath(), "industrialkitchen.cfg"));
        //ModConfig.readConfig();
        ModContainer mod = Loader.instance().getReversedModObjectList().get(IndustrialKitchen.instance);

        setupDirectories(e.getModConfigurationDirectory());
        createMissingDirectories();

        Configs.load(mod, configDirectory);

        ModFluids.register();
    }

    public void init(FMLInitializationEvent e)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(IndustrialKitchen.instance, new GuiProxy());

        ModItems.registerEvents();
    }

    @SuppressWarnings("EmptyMethod")
    public void postInit(FMLPostInitializationEvent e)
    {
        //if(config.hasChanged())
        //{
        //    config.save();
        //}
    }

    protected void setupDirectories(File directory)
    {
        modDirectory = new File(directory.getPath(), IndustrialKitchen.modId);
        //configDirectory = new File(modDirectory.getPath(), "config");
        configDirectory = modDirectory;
    }

    private void createMissingDirectories()
    {
        if (!modDirectory.exists() && !modDirectory.mkdir())
        {
            IndustrialKitchen.logger.error("Unable to create config directory");
        }
        if (!configDirectory.exists())
        {
            if(!configDirectory.mkdir())
            {
                IndustrialKitchen.logger.error("Unable to create config directory");
            }
        }
    }
}
