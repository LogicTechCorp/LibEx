/*
 * LibEx
 * Copyright (c) 2017 by MineEx
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

package lex.world.biome;

import lex.config.IConfig;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeWrapperNether extends BiomeWrapperLibEx
{
    BiomeWrapperNether(Builder builder)
    {
        super(builder);
    }

    public static class Builder extends LibExBiomeWrapperBuilder
    {
        public Builder()
        {
            super("nether");
        }

        @Override
        public Builder configure(IConfig config)
        {
            biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(config.getString("biome")));

            if(biome == null)
            {
                biome = Biomes.HELL;
            }

            blocks.put("oceanBlock", config.getBlock("oceanBlock", Blocks.LAVA.getDefaultState()));
            blocks.put("wallBlock", config.getBlock("wallBlock", biome.fillerBlock));
            blocks.put("ceilingBottomBlock", config.getBlock("ceilingBottomBlock", biome.fillerBlock));
            blocks.put("ceilingFillerBlock", config.getBlock("ceilingFillerBlock", biome.fillerBlock));
            super.configure(config);
            return this;
        }

        @Override
        public BiomeWrapperNether create()
        {
            return new BiomeWrapperNether(this);
        }
    }
}
