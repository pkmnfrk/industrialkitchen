package com.mike_caron.industrialkitchen.tileentity.appliance;

import com.mike_caron.industrialkitchen.block.kitchen.BlockKitchenPlug;
import com.mike_caron.industrialkitchen.heat.CapabilityHeatSink;
import com.mike_caron.industrialkitchen.heat.ConstantHeatSink;
import com.mike_caron.industrialkitchen.heat.IHeatSink;
import com.mike_caron.industrialkitchen.heat.MaterialHeatSink;
import com.mike_caron.industrialkitchen.item.ModItems;
import com.mike_caron.industrialkitchen.item.cookware.ItemPan;
import com.mike_caron.industrialkitchen.item.cookware.ItemPot;
import com.mike_caron.industrialkitchen.tileentity.kitchen.TileEntityKitchenPlug;
import com.mike_caron.mikesmodslib.integrations.ITOPInfoProvider;
import com.mike_caron.mikesmodslib.util.TileEntityProxy;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.cyclops.commoncapabilities.api.capability.temperature.DefaultTemperature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntityHotplate
    extends TileEntityAppliance
    implements ITickable, ITOPInfoProvider
{
    ItemStack tool;

    final ToolFluidAdapter adapter = new ToolFluidAdapter();
    final ConstantHeatSink hotPlate = new ConstantHeatSink(DefaultTemperature.ZERO_CELCIUS + 200);

    TileEntityProxy<TileEntityKitchenPlug> plug = new TileEntityProxy<TileEntityKitchenPlug>()
    {
        @Nullable
        @Override
        protected BlockPos findTileEntity()
        {
            return BlockKitchenPlug.findClosest(world, getPos().offset(EnumFacing.DOWN));
        }

        @Override
        protected void markDirty()
        {
            TileEntityHotplate.this.markAndNotify();
        }
    };

    public EnumTool getTool()
    {
        if(tool == null)
            return EnumTool.NONE;

        if(tool.getItem() == ModItems.pan)
            return EnumTool.PAN;

        if(tool.getItem() == ModItems.pot)
            return EnumTool.POT;

        return EnumTool.NONE;
    }

    public boolean isValidTool(@Nonnull ItemStack itemStack)
    {
        if(itemStack.isEmpty())
            return true;

        if(itemStack.getItem() instanceof ItemPan)
            return true;

        if(itemStack.getItem() instanceof ItemPot)
            return true;

        return false;
    }

    @Nonnull
    public ItemStack insertTool(@Nonnull ItemStack itemStack)
    {
        if(!isValidTool(itemStack))
        {
            return itemStack;
        }

        ItemStack ret = tool;

        IBlockState oldState = world.getBlockState(pos);

        if(itemStack.isEmpty())
        {
            tool = null;
        }
        else
        {
            tool = itemStack;
        }

        IBlockState newState = world.getBlockState(pos);

        markAndNotify(oldState, newState);

        if(ret == null)
            ret = ItemStack.EMPTY;

        return ret;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        tool = null;

        if(compound.hasKey("tool"))
        {
            tool = new ItemStack(compound.getCompoundTag("tool"));
        }

        if(compound.hasKey("plug"))
        {
            plug.deserializeNBT(compound.getCompoundTag("plug"));
        }

        if(world != null && world.isRemote)
        {
            markAndNotify();
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound ret = super.writeToNBT(compound);

        if(tool != null)
        {
            ret.setTag("tool", tool.serializeNBT());
        }

        ret.setTag("plug", plug.serializeNBT());

        return ret;
    }

    public boolean isValid()
    {
        return plug.isValid();
    }

    @Override
    public void update()
    {
        //TileEntityKitchenPlug plugEntity = plug.getTileEntity(world);
        //temp:

        if (isValid())
        {
            hotPlate.setTemperature(DefaultTemperature.ZERO_CELCIUS + 200);
        }
        else
        {
            hotPlate.setTemperature(DefaultTemperature.ZERO_CELCIUS + 20);
        }

        if(tool != null)
        {
            IHeatSink heat = tool.getCapability(CapabilityHeatSink.CAPABILITY, null);

            if (heat != null && isValid())
            {
                MaterialHeatSink.equalize(hotPlate, heat);
            }

            tool.getItem().onUpdate(tool, world, null, 0, true);
            markDirty();
        }


    }

    @Override
    public void refreshConnections()
    {
        plug.invalidate();
    }

    public enum EnumTool
        implements IStringSerializable
    {
        NONE("none"),
        PAN("pan"),
        POT("pot");

        private String name;

        EnumTool(String name)
        {
            this.name = name;
        }

        @Override
        @Nonnull
        public String getName()
        {
            return name;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return adapter.isActive();
        }
        else if(capability == CapabilityHeatSink.CAPABILITY && tool != null)
        {
            return tool.hasCapability(capability, facing);
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            if(adapter.isActive())
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(adapter);
        }
        else if(capability == CapabilityHeatSink.CAPABILITY && tool != null)
        {
            return tool.getCapability(capability, facing);
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
    {
        if(tool != null)
        {
            List<String> tooltip = tool.getTooltip(player, ITooltipFlag.TooltipFlags.NORMAL);

            for(String tt : tooltip)
            {
                probeInfo.text(tt);
            }
        }
    }

    @Override
    public boolean hasInfo(EntityPlayer player)
    {
        return tool != null;
    }

    class ToolFluidAdapter
        implements IFluidHandler
    {
        public boolean isActive()
        {
            return tool != null && tool.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        }

        @Nonnull
        private IFluidHandlerItem getItem()
        {
            IFluidHandlerItem ret = tool.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);

            assert ret != null;

            return ret;
        }

        @Override
        public IFluidTankProperties[] getTankProperties()
        {
            IFluidHandlerItem item = getItem();

            return item.getTankProperties();
        }

        @Override
        public int fill(FluidStack resource, boolean doFill)
        {
            IFluidHandlerItem item = getItem();

            return item.fill(resource, doFill);
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain)
        {
            IFluidHandlerItem item = getItem();

            return item.drain(resource, doDrain);
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain)
        {
            IFluidHandlerItem item = getItem();

            return item.drain(maxDrain, doDrain);
        }
    }
}
