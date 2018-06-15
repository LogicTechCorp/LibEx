/*
 * LibEx
 * Copyright (c) 2017-2018 by MineEx
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

package lex.block;

import lex.api.IModData;
import lex.tileentity.TileEntityInventory;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTileEntity<T extends TileEntity> extends BlockLibEx
{
    private final Class<T> cls;

    public BlockTileEntity(IModData data, String name, Material material, Class<T> clsIn)
    {
        super(data, name, material);
        GameRegistry.registerTileEntity(clsIn, new ResourceLocation(data.getModId() + ":" + name));
        cls = clsIn;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        try
        {
            return cls.newInstance();
        }
        catch(InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity != null)
        {
            if(tileEntity instanceof TileEntityInventory)
            {
                ((TileEntityInventory) tileEntity).dropInventoryItems(world, pos);
            }
        }

        super.breakBlock(world, pos, state);
    }
}
