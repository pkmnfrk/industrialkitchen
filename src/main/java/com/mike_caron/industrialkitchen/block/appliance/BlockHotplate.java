package com.mike_caron.industrialkitchen.block.appliance;

import com.mike_caron.industrialkitchen.block.Props;
import com.mike_caron.industrialkitchen.tileentity.appliance.TileEntityHotplate;
import com.mike_caron.mikesmodslib.block.BlockBase;
import com.mike_caron.mikesmodslib.util.FluidUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockHotplate
    extends BlockBase
{
    public static final String ID = "hotplate";
    private static final AxisAlignedBB BOUNDING_BOX_EMPTY = new AxisAlignedBB(
        1/16.0, 0, 1/16.0,
        15/16.0, 1/8.0, 15/16.0
    );
    private static final AxisAlignedBB BOUNDING_BOX_PAN = new AxisAlignedBB(
        1/16.0, 0, 1/16.0,
        15/16.0, 3/8.0, 15/16.0
    );
    private static final AxisAlignedBB BOUNDING_BOX_POT = new AxisAlignedBB(
        1/16.0, 0, 1/16.0,
        15/16.0, 3/4.0, 15/16.0
    );
    private static final PropertyEnum<TileEntityHotplate.EnumTool> TOOL = PropertyEnum.create("tool", TileEntityHotplate.EnumTool.class);

    public BlockHotplate()
    {
        super(Material.IRON, ID);

        this.setDefaultState(addStateProperties(this.blockState.getBaseState()));
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state)
    {
        return new TileEntityHotplate();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote || hand != EnumHand.MAIN_HAND)
            return true; //super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

        TileEntityHotplate tileEntity = getTileEntity(worldIn, pos, TileEntityHotplate.class);
        if(tileEntity != null)
        {

            if(FluidUtils.fillPlayerHandWithFluid(worldIn, pos, playerIn, tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)))
                return true;

            if(FluidUtils.drainPlayerHandOfFluid(worldIn, pos, playerIn, tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)))
                return true;

            ItemStack handItem = playerIn.getHeldItem(hand);

            handItem = tileEntity.insertTool(handItem);

            playerIn.setHeldItem(hand, handItem);

            return true;
        }

        return false;
    }

    @Override
    protected IBlockState addStateProperties(IBlockState blockState)
    {
        return super.addStateProperties(blockState)
            .withProperty(TOOL, TileEntityHotplate.EnumTool.PAN)
            .withProperty(Props.IS_LIT, false);
    }

    @Override
    protected void addAdditionalPropeties(List<IProperty<?>> properties)
    {
        super.addAdditionalPropeties(properties);

        properties.add(TOOL);
        properties.add(Props.IS_LIT);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    @Nonnull
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntityHotplate te = getTileEntity(worldIn, pos, TileEntityHotplate.class);
        if(te != null)
        {
            state = state
                .withProperty(TOOL, te.getTool())
                .withProperty(Props.IS_LIT, te.isValid());
        }
        else
        {
            state = state
                .withProperty(TOOL, TileEntityHotplate.EnumTool.NONE)
                .withProperty(Props.IS_LIT, false);
        }

        return state;
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        TileEntityHotplate te = getTileEntity(source, pos, TileEntityHotplate.class);
        if(te != null)
        {
            switch(te.getTool())
            {
                case PAN:
                    return BOUNDING_BOX_PAN;
                case POT:
                    return BOUNDING_BOX_POT;
            }
        }
        return BOUNDING_BOX_EMPTY;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
