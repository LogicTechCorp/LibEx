package lex.block;

import lex.IModData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class BlockOreLibEx extends BlockLibEx
{
    private ResourceLocation dropRegistryName;
    private Item drop;

    public BlockOreLibEx(IModData data, String name, int harvestLevel, float hardness, float resistance, float lightLevel)
    {
        super(data, name, Material.ROCK);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setLightLevel(lightLevel);
    }

    public BlockOreLibEx(IModData data, String name, int harvestLevel, ResourceLocation dropRegistryName, float hardness, float resistance, float lightLevel)
    {
        super(data, name, Material.ROCK);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setLightLevel(lightLevel);
        this.dropRegistryName = dropRegistryName;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(this.drop == null)
        {
            if(this.dropRegistryName != null)
            {
                Item dropItem = ForgeRegistries.ITEMS.getValue(this.dropRegistryName);
                Block dropBlock = ForgeRegistries.BLOCKS.getValue(this.dropRegistryName);

                if(dropItem != null)
                {
                    this.drop = dropItem;
                }
                else if(dropBlock != null)
                {
                    this.drop = Item.getItemFromBlock(dropBlock);
                }
            }

            if(this.drop == null)
            {
                this.drop = Item.getItemFromBlock(this);
            }
        }

        return this.drop;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand)
    {
        if(fortune > 0)
        {
            int i = rand.nextInt(fortune + 2) - 1;

            if(i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(rand) * (i + 1);
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        return MathHelper.getInt(rand, 2, 5);
    }
}
