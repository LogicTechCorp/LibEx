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

package lex.item;

import lex.block.BlockSlabLibEx;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemBlockSlabLibEx extends ItemSlab
{
    public ItemBlockSlabLibEx(Block block, BlockSlabLibEx singleSlab, BlockSlabLibEx doubleSlab)
    {
        super(block, singleSlab, doubleSlab);
        setRegistryName(block.getRegistryName().toString());
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return ((BlockSlabLibEx) block).getTranslationKey(stack.getMetadata());
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }

}
