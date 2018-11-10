package com.TheRPGAdventurer.ROTD.server.cmd;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

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
			World world = sender.getEntityWorld();
			EntityTameableDragon dragon = new EntityTameableDragon(world);
			if(dragon.isMale()) {
				applyModifier(server, sender, dragon1 -> dragon.setMale(false));
			} else if(!dragon.isMale()) {
				applyModifier(server, sender, dragon1 -> dragon.setMale(true));
			}
		}
	}
}
