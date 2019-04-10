package com.mike_caron.industrialkitchen.block;

import com.mike_caron.industrialkitchen.IndustrialKitchen;
import com.mike_caron.industrialkitchen.block.appliance.BlockHotplate;
import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenBlank;
import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenOven;
import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenPlug;
import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenTap;
import com.mike_caron.industrialkitchen.tileentity.appliance.TileEntityHotplate;
import com.mike_caron.industrialkitchen.tileentity.kitchen.TileEntityKitchenOven;
import com.mike_caron.industrialkitchen.tileentity.kitchen.TileEntityKitchenPlug;
import com.mike_caron.mikesmodslib.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
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
@GameRegistry.ObjectHolder (IndustrialKitchen.modId)
public class ModBlocks
{
    @GameRegistry.ObjectHolder(BlockKitchenBlank.ID)
    public static BlockKitchenBlank kitchenBlank;

    @GameRegistry.ObjectHolder(BlockKitchenTap.ID)
    public static BlockKitchenTap kitchenTap;

    @GameRegistry.ObjectHolder(BlockKitchenPlug.ID)
    public static BlockKitchenPlug kitchenPlug;

    @GameRegistry.ObjectHolder(BlockKitchenOven.ID)
    public static BlockKitchenOven kitchenOven;

    @GameRegistry.ObjectHolder(BlockHotplate.ID)
    public static BlockHotplate hotplate;


    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(new BlockKitchenBlank());
        registry.register(new BlockKitchenTap());
        registry.register(new BlockKitchenPlug());
        registry.register(new BlockKitchenOven());
        registry.register(new BlockHotplate());

        GameRegistry.registerTileEntity(TileEntityHotplate.class, new ResourceLocation(IndustrialKitchen.modId, BlockHotplate.ID));
        GameRegistry.registerTileEntity(TileEntityKitchenPlug.class, new ResourceLocation(IndustrialKitchen.modId, BlockKitchenPlug.ID));
        GameRegistry.registerTileEntity(TileEntityKitchenOven.class, new ResourceLocation(IndustrialKitchen.modId, BlockKitchenOven.ID));
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        try
        {
            for (Field field : ModBlocks.class.getDeclaredFields())
            {
                if (Modifier.isStatic(field.getModifiers()) && BlockBase.class.isAssignableFrom(field.getType()))
                {
                    BlockBase block = (BlockBase) field.get(null);

                    registry.register(
                            new ItemBlock(block)
                            .setRegistryName(block.getRegistryName())
                    );
                }
            }
        }
        catch(IllegalAccessException ex)
        {
            throw new RuntimeException("Unable to reflect upon myelf??");
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        try
        {
            for (Field field : ModBlocks.class.getDeclaredFields())
            {
                if (Modifier.isStatic(field.getModifiers()) && BlockBase.class.isAssignableFrom(field.getType()))
                {
                    BlockBase block = (BlockBase) field.get(null);

                    block.initModel();
                }
            }
        }
        catch(IllegalAccessException ex)
        {
            throw new RuntimeException("Unable to reflect upon myelf??");
        }
    }
}
