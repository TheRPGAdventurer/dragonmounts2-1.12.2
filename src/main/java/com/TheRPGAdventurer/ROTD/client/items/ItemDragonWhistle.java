package com.TheRPGAdventurer.ROTD.client.items;

import java.util.BitSet;
import java.util.List;

import javax.annotation.Nullable;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.client.sound.ModSounds;
import com.TheRPGAdventurer.ROTD.client.userinput.StatCollector;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonControl;
import com.TheRPGAdventurer.ROTD.server.network.MessageDragonWhistle;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
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
		EntityTameableDragon dragon = new EntityTameableDragon(worldIn);
		if(dragon.isTamedFor(player)) {
			dragon.setSitting(true);
		   worldIn.playSound(player, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1, 1);	
		}
	//	if(commands == null) {
	//		setCommands(Commands.CIRCLE);
	//	}
		
	//	if(dragon.isTamed() && dragon.isTamedFor(player)) {
	//		worldIn.playSound(player, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.PLAYERS, 1, 1);	
	//		switch(commands) {
	//		case CIRCLE:
	//			dragon.Whistle().set(0);
	//			break;
	//		case COME:
	//			break;
	//		default:
	//			break;
	///		
	//		}
	//		
	//	}
		
	//	if(player.isSneaking()) {		
	//		setCommands(Commands.CIRCLE);
	//	} 
		//else {
	///		if(dragon.isTamedFor(player)) {
	///		   switch(getCommands()) {
	///		     case CIRCLE:
	//			  worldIn.playSound(player, player.getPosition(), ModSounds.DRAGON_WHISTLE, SoundCategory.NEUTRAL, 1, 1);				
	///			  break;
	///		     case COME:
	//			  worldIn.playSound(player, player.getPosition(), ModSounds.DRAGON_WHISTLE1, SoundCategory.NEUTRAL, 1, 1);
	///			  break;
	//		     default:
	///			  break;		
	//		    }
	//		}
	//	}
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
