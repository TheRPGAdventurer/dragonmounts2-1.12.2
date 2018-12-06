package com.TheRPGAdventurer.ROTD.client.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.server.entity.IDragonWhistle;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget.Sorter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDragonWhistle extends Item {
	
	ItemDragonWhistle.Commands commands;
    private final MessageDragonWhistle dcw = new MessageDragonWhistle();
	
	public ItemDragonWhistle() {
		this.setUnlocalizedName("dragon_whistle");
		this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "dragon_whistle"));
		this.setMaxStackSize(1);
		this.setCreativeTab(DragonMounts.TAB);
	}
	
	public void setCommands(ItemDragonWhistle.Commands commands) {
		this.commands = commands;
	}
	
	public ItemDragonWhistle.Commands getCommands() {
		return commands;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//		tooltip.add(StatCollector.translateToLocal(commands.toString().toLowerCase()));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
		ItemStack itemStackIn = player.getHeldItem(hand);

		float chunksize = 16 * 9;
		@SuppressWarnings("unused")
		List<Entity> list = worldIn.<Entity>getEntitiesWithinAABBExcludingEntity(player, (new AxisAlignedBB(player.posX, player.posY, player.posZ, player.posX + 1.0D, player.posY + 1.0D, player.posZ + 1.0D)).grow(chunksize, 256, chunksize));
		Collections.sort(list, new Sorter(player));
		List<IDragonWhistle> dragons = new ArrayList<IDragonWhistle>();
		Iterator<Entity> itr_entities = list.iterator();
		while (itr_entities.hasNext()) {
			Entity entity = itr_entities.next();
			if (entity instanceof IDragonWhistle) {
				dragons.add((IDragonWhistle) entity);
			}
		}

		Iterator<IDragonWhistle> itr_dragons = dragons.iterator();
		while (itr_dragons.hasNext()) {
			IDragonWhistle dragon = itr_dragons.next();
			dragon.Whistle(player);
			/*
			if(dragon.isTamed() && dragon.isOwner(player)) {
                if (dragon.isFlying() || dragon.isHovering()) {
                    dragon.setFlying(false);
                    dragon.setHovering(false);
                }
            }*/
		}
		
		worldIn.playSound(player, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1, 1);			
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
  //  @SubscribeEvent
  ///  public void onTick(ClientTickEvent evt) {
  //      BitSet flags = dcw.getFlags();
  //      flags.set(0, commands == Commands.COME);
  //      flags.set(1, commands == Commands.CIRCLE);
  //      flags.set(2, KEY_HOVERCANCEL.isPressed());
        
        // send message to server if it has changed
   //     if (dcw.hasChanged()) {
    //        DragonMounts.NETWORK_WRAPPER.sendToServer(dcw);
   //     }
  //  }
    
	public enum Commands {
	    COME(),
		CIRCLE();		
		
	}
}
