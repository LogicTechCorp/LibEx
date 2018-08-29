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

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockLibEx extends ItemBlock
{
    public ItemBlockLibEx(Block block)
    {
        super(block);
        this.setRegistryName(block.getRegistryName().toString());
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }
}
