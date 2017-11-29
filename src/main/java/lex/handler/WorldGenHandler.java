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

package lex.handler;

import lex.LibEx;
import lex.util.NumberHelper;
import lex.world.biome.BiomeWrapper;
import lex.world.biome.BiomeWrapperManager;
import lex.world.gen.GenerationStage;
import lex.world.gen.feature.IFeature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = LibEx.MOD_ID)
public class WorldGenHandler
{
    @SubscribeEvent
    public static void onPrePopulateChunk(PopulateChunkEvent.Pre event)
    {
        generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.PRE_POPULATE);
    }

    @SubscribeEvent
    public static void onPopulateChunk(PopulateChunkEvent.Populate event)
    {
        if(event.getType() == PopulateChunkEvent.Populate.EventType.CUSTOM)
        {
            generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.POPULATE);
        }
    }

    @SubscribeEvent
    public static void onPostPopulateChunk(PopulateChunkEvent.Post event)
    {
        generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.POST_POPULATE);
    }

    @SubscribeEvent
    public static void onPreBiomeDecorate(DecorateBiomeEvent.Pre event)
    {
        generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.PRE_DECORATE);
    }

    @SubscribeEvent
    public static void onBiomeDecorate(DecorateBiomeEvent.Decorate event)
    {
        if(event.getType() == DecorateBiomeEvent.Decorate.EventType.CUSTOM)
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.DECORATE);
        }
    }

    @SubscribeEvent
    public static void onPostBiomeDecorate(DecorateBiomeEvent.Post event)
    {
        generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.POST_DECORATE);
    }

    @SubscribeEvent
    public static void onPreGenerateOres(OreGenEvent.Pre event)
    {
        generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.PRE_ORE);
    }

    @SubscribeEvent
    public static void onGenerateOres(OreGenEvent.GenerateMinable event)
    {
        if(event.getType() == OreGenEvent.GenerateMinable.EventType.CUSTOM)
        {
            generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.ORE);
        }
    }

    @SubscribeEvent
    public static void onPostGenerateOres(OreGenEvent.Post event)
    {
        generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.POST_ORE);
    }


    private static void generateFeature(World world, int chunkX, int chunkZ, Random rand, GenerationStage generationStage)
    {
        BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        generateFeature(world, pos, rand, generationStage);
    }

    private static void generateFeature(World world, BlockPos pos, Random rand, GenerationStage generationStage)
    {
        BiomeWrapper wrapper = BiomeWrapperManager.getBiomeWrapper(world.getBiome(pos.add(16, 0, 16)));

        for(IFeature feature : wrapper.getFeatureList(generationStage))
        {
            for(int generationAttempts = 0; generationAttempts < feature.getGenerationAttempts(rand); generationAttempts++)
            {
                feature.generate(world, rand, pos.add(rand.nextInt(16) + 8, NumberHelper.getNumberInRange(feature.getMinHeight(), feature.getMaxHeight(), rand), rand.nextInt(16) + 8));
            }
        }
    }
}
