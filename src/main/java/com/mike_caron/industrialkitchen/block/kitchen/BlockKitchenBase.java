package com.mike_caron.industrialkitchen.block.kitchen;

import com.mike_caron.mikesmodslib.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class BlockKitchenBase
    extends BlockBase
{
    public static final PropertyBool CONNECTED_DOWN = PropertyBool.create("connected_down");
    public static final PropertyBool CONNECTED_UP = PropertyBool.create("connected_up");
    public static final PropertyBool CONNECTED_NORTH = PropertyBool.create("connected_north");
    public static final PropertyBool CONNECTED_SOUTH = PropertyBool.create("connected_south");
    public static final PropertyBool CONNECTED_WEST = PropertyBool.create("connected_west");
    public static final PropertyBool CONNECTED_EAST = PropertyBool.create("connected_east");

    public BlockKitchenBase(String name)
    {
        super(Material.IRON, name);

        setDefaultState(addStateProperties(this.blockState.getBaseState()));
    }

    @Override
    protected void addAdditionalPropeties(List<IProperty<?>> properties)
    {
        super.addAdditionalPropeties(properties);

        properties.add(CONNECTED_DOWN);
        properties.add(CONNECTED_UP);
        properties.add(CONNECTED_NORTH);
        properties.add(CONNECTED_SOUTH);
        properties.add(CONNECTED_EAST);
        properties.add(CONNECTED_WEST);

    }

    @Override
    protected IBlockState addStateProperties(IBlockState blockState)
    {
        return super.addStateProperties(blockState)
            .withProperty(CONNECTED_DOWN, false)
            .withProperty(CONNECTED_UP, false)
            .withProperty(CONNECTED_NORTH, false)
            .withProperty(CONNECTED_SOUTH, false)
            .withProperty(CONNECTED_EAST, false)
            .withProperty(CONNECTED_WEST, false)
        ;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return super
            .getActualState(state, worldIn, pos)
            .withProperty(CONNECTED_DOWN,  isSideConnectable(worldIn, pos, EnumFacing.DOWN ))
            .withProperty(CONNECTED_UP,    isSideConnectable(worldIn, pos, EnumFacing.UP ))
            .withProperty(CONNECTED_NORTH, isSideConnectable(worldIn, pos, EnumFacing.NORTH ))
            .withProperty(CONNECTED_SOUTH, isSideConnectable(worldIn, pos, EnumFacing.SOUTH ))
            .withProperty(CONNECTED_EAST,  isSideConnectable(worldIn, pos, EnumFacing.EAST ))
            .withProperty(CONNECTED_WEST,  isSideConnectable(worldIn, pos, EnumFacing.WEST))
        ;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        //return super.getStateFromMeta(meta);
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        //return super.getMetaFromState(state);
        return 0;
    }

    /**
     * Checks if a specific side of a block can connect to this block. For this example, a side
     * is connectable if the block is the same block as this one.
     *
     * @param world The world to run the check in.
     * @param pos The position of the block to check for.
     * @param side The side of the block to check.
     * @return Whether or not the side is connectable.
     */
    private boolean isSideConnectable (IBlockAccess world, BlockPos pos, EnumFacing side) {

        final IBlockState state = world.getBlockState(pos.offset(side));
        return state.getBlock() instanceof BlockKitchenBase;
    }

    public static BlockPos findClosest(@Nonnull World world, @Nonnull BlockPos start, Class<? extends BlockKitchenBase> search)
    {
        HashSet<Long> explored = new HashSet<>();
        PriorityQueue<DistPos> toExplore = new PriorityQueue<>(DistPos::compare);

        toExplore.add(new DistPos(start, 0));

        while(!toExplore.isEmpty())
        {
            DistPos spot = toExplore.remove();

            if(explored.contains(spot.toLong()))
                continue;

            explored.add(spot.toLong());

            IBlockState block = world.getBlockState(spot);

            if(!(block.getBlock() instanceof BlockKitchenBase))
                continue;

            if(search.isInstance(block.getBlock()))
                return spot;

            for(EnumFacing dir : EnumFacing.VALUES)
            {
                toExplore.add(spot.offset(dir));
            }
        }

        return null;
    }

    private static class DistPos
        extends BlockPos
    {
        public final int dist;

        DistPos(BlockPos pos, int distance)
        {
            super(pos.getX(), pos.getY(), pos.getZ());

            this.dist = distance;
        }

        public static int compare(DistPos a, DistPos b)
        {
            int ret = b.dist - a.dist;

            if(ret != 0) return ret;

            return a.compareTo(b);
        }

        @Override
        @Nonnull
        public DistPos offset(@Nonnull EnumFacing facing)
        {
            return new DistPos(super.offset(facing), dist + 1);
        }
    }
}
