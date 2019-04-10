package com.mike_caron.industrialkitchen.block.kitchen;

import com.mike_caron.industrialkitchen.block.Props;
import com.mike_caron.industrialkitchen.tileentity.kitchen.TileEntityKitchenOven;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockKitchenOven
    extends BlockKitchenBase
{
    public static final String ID = "kitchen_oven";

    public BlockKitchenOven()
    {
        super(ID);
    }

    @Override
    protected void addAdditionalPropeties(List<IProperty<?>> properties)
    {
        super.addAdditionalPropeties(properties);

        properties.add(Props.IS_LIT);
    }

    @Override
    protected IBlockState addStateProperties(IBlockState blockState)
    {
        return super.addStateProperties(blockState)
            .withProperty(Props.IS_LIT, false)
            ;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntityKitchenOven te = getTileEntity(worldIn, pos, TileEntityKitchenOven.class);

        state = super.getActualState(state, worldIn, pos);

        state = state.withProperty(Props.IS_LIT, te != null && te.isValid());

        return state;
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
        return new TileEntityKitchenOven();
    }
}
