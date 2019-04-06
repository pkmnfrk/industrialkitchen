package com.mike_caron.industrialkitchen.block.appliance;

import com.mike_caron.industrialkitchen.tileentity.TileEntityHotplate;
import com.mike_caron.mikesmodslib.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockHotplate
    extends BlockBase
{
    public static final String ID = "hotplate";
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
        1/16.0, 0, 1/16.0,
        15/16.0, 1/8.0, 15/16.0
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
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityHotplate();
    }

    @Override
    protected IBlockState addStateProperties(IBlockState blockState)
    {
        return super.addStateProperties(blockState)
            .withProperty(TOOL, TileEntityHotplate.EnumTool.PAN);
    }

    @Override
    protected void addAdditionalPropeties(List<IProperty<?>> properties)
    {
        super.addAdditionalPropeties(properties);

        properties.add(TOOL);
    }

    @Override
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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntityHotplate te = (TileEntityHotplate)worldIn.getTileEntity(pos);
        if(te != null)
        {
            state = state.withProperty(TOOL, te.getTool());
        }

        return state;
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
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
