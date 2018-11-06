package com.TheRPGAdventurer.ROTD.server.cmd;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

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
			
		}
	}
}
