package com.TheRPGAdventurer.ROTD.cmd;

import com.TheRPGAdventurer.ROTD.inits.ModSounds;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;

public class CommandDragonGender extends CommandBase implements IDragonModifier {

	@Override
	public String getName() {
		return "gender";
	}

	@Override
	public String getUsage(ICommandSender sender) {
        return String.format("%s [username]", getName());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender instanceof EntityPlayerMP) {
			applyModifier(server, sender, dragon -> dragon.setOppositeGender());
			((EntityPlayerMP) sender).world.playSound(null, sender.getPosition(), ModSounds.DRAGON_SWITCH, SoundCategory.NEUTRAL, 1, 1);
		}
	}
}
