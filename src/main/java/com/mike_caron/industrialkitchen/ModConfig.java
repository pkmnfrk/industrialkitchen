package com.mike_caron.industrialkitchen;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = IndustrialKitchen.modId)
@Config.LangKey("industrialkitchen.config.title")
public class ModConfig
{
    private static final String CATEGORY_GENERAL = "general";

    /*
    @Config.Comment("The amount to multiply EMC consumption by in the Transmutation Generator. Does not, directly, affect energy production")
    @Config.RangeDouble(min = 0.01, max = 100.0)
    @Config.LangKey("industrialkitchen.config.generatoremcmultiplier")
    public static double generatorEMCMultiplier = 1.0;

    @Config.Comment("The maximum amount of a single item to expose, even if you have enough EMC to make more.")
    @Config.RangeInt(min = 64)
    @Config.LangKey("industrialkitchen.config.maximumexposedstacksize")
    public static int maximumExposedStackSize = 1000000;

    @Config.Comment("Whether to expose a Soulbound Talisman linked to an invalid player. Only enable if you know what this means.")
    @Config.LangKey("industrialkitchen.config.exposeinvalidtalisman")
    public static boolean exposeInvalidTalisman = false;

    @Config.Comment("Whether to expose bunch of dummy items. You really don't want to do this.")
    @Config.LangKey("industrialkitchen.config.exposedummytestitems")
    public static boolean exposeDummyTestItems = false;

*/
    @Mod.EventBusSubscriber(modid = IndustrialKitchen.modId)
    private static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if(event.getModID().equals(IndustrialKitchen.modId))
            {

                ConfigManager.sync(IndustrialKitchen.modId, Config.Type.INSTANCE);
            }
        }
    }
}
