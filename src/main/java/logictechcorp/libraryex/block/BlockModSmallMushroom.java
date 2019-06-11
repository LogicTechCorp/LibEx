/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.libraryex.block;

import logictechcorp.libraryex.block.property.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public abstract class BlockModSmallMushroom extends BlockMod implements IPlantable, IGrowable
{
    private static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.4000000059604645D, 0.699999988079071D);
    protected EnumPlantType plantType;

    public BlockModSmallMushroom(ResourceLocation registryName, BlockProperties properties, EnumPlantType plantType)
    {
        super(registryName, properties);
        this.plantType = plantType;
    }

    @Override
    public abstract void grow(World world, Random random, BlockPos pos, IBlockState state);

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return MUSHROOM_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        if(super.canPlaceBlockAt(world, pos))
        {
            if(pos.getY() >= 0 && pos.getY() < 256)
            {
                IBlockState soil = world.getBlockState(pos.down());
                return soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
            }
        }

        return false;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        IBlockState soil = world.getBlockState(pos.down());

        if(!soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this))
        {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
    {
        IBlockState soil = world.getBlockState(pos.down());
        return !isClient && soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos pos, IBlockState state)
    {
        return false;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return this.plantType;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);

        if(state.getBlock() != this)
        {
            return this.getDefaultState();
        }

        return state;

    }
}
