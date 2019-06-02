/*
** 2016 March 09
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.blocks;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.helper.EnumDragonLifeStage;
import com.TheRPGAdventurer.ROTD.inits.ModBlocks;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import com.TheRPGAdventurer.ROTD.util.StatCollector;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class BlockDragonBreedEgg extends Block implements IHasModel {
    
    public static final PropertyEnum<EnumDragonBreed> BREED = PropertyEnum.create("breed", EnumDragonBreed.class);
    private EnumDragonBreed breed;
    
    public BlockDragonBreedEgg(String name, EnumDragonBreed breed) {
    	super(Material.WOOD);
        setUnlocalizedName(name);
        setRegistryName(name);
        setHardness(0);
        setResistance(30);
        setSoundType(SoundType.WOOD);
        setLightLevel(0.125f);
        setCreativeTab(DragonMounts.mainTab);
        this.breed = breed;
        
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
    	return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BREED);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }
    
    @Override 
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.checkFall(worldIn, pos);
    }
    
    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if (worldIn.provider.getDimensionType() == DimensionType.THE_END) {
    		playerIn.sendStatusMessage(new TextComponentTranslation(StatCollector.translateToLocal("egg.cantHatchEnd.DragonMounts")), true);
    		return false;
    	}
    	
    	worldIn.setBlockToAir(pos);
    	
    	EntityTameableDragon entityDragon = new EntityTameableDragon(worldIn);
    	entityDragon.setBreedType(breed);
    	entityDragon.getLifeStageHelper().setLifeStage(EnumDragonLifeStage.EGG);
    	entityDragon.getReproductionHelper().setBreeder(playerIn);
    	entityDragon.setPosition(pos.getX(), pos.getY(), pos.getZ());
    	
        worldIn.spawnEntity(entityDragon);
        
        return true;
    }
    
    private void checkFall(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down()) && BlockFalling.canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {

            if (!BlockFalling.fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
                worldIn.spawnEntity(new EntityFallingBlock(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), this.getDefaultState()));
            } else {
                worldIn.setBlockToAir(pos);
                BlockPos blockpos;

                for (blockpos = pos; worldIn.isAirBlock(blockpos) && BlockFalling.canFallThrough(worldIn.getBlockState(blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down()) {}

                if (blockpos.getY() > 0) {
                    worldIn.setBlockState(blockpos, this.getDefaultState(), 2);
                }
            }
        }
    }

	@Override
	public void RegisterModels() {
		DragonMounts.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
     
}