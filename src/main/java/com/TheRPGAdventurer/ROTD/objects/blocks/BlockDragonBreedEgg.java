/*
** 2016 March 09
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.blocks;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModBlocks;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper.EnumDragonLifeStage;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import com.TheRPGAdventurer.ROTD.util.StatCollector;

import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;


/**
 * Handles a custom dragon egg block that on block activation, spawns a dragon entity at the egg lifestage. (Starts the hatching process)
 * 
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * @author WolfShotz
 */
public class BlockDragonBreedEgg extends BlockDragonEgg implements IHasModel {
	
    private EnumDragonBreed breed;
    
    public BlockDragonBreedEgg(String name, EnumDragonBreed breed) {
        setUnlocalizedName(breed.getName().toLowerCase() + "_egg");
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
    
    /**
     * Called when the block is right clicked by a player.
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if (worldIn.isRemote) return false;
    	if (worldIn.provider.getDimensionType() == DimensionType.THE_END) {
    		playerIn.sendStatusMessage(new TextComponentTranslation(StatCollector.translateToLocal("egg.cantHatchEnd.DragonMounts")), true);
    		return false;
    	}
    	
    	worldIn.setBlockToAir(pos);
    	
    	EntityTameableDragon entityDragon = new EntityTameableDragon(worldIn);
    	entityDragon.setBreedType(breed);
    	entityDragon.getLifeStageHelper().setLifeStage(EnumDragonLifeStage.EGG);
    	entityDragon.getReproductionHelper().setBreeder(playerIn);
    	entityDragon.setPosition(pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5);
    	
        worldIn.spawnEntity(entityDragon);
        
        return true;
    }

	@Override
	public void RegisterModels() {
		DragonMounts.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
     
}