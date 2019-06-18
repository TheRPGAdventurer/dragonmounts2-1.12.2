package com.TheRPGAdventurer.ROTD.cmd;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandDragonWhistle extends CommandBaseDragon {

	public CommandDragonWhistle(String name) {
		super(name);
	}
	
	@Override
	public String getName() {
		return "whistle";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player;
            if (args.length > 0) {
                player = getPlayer(server, sender, args[0]);
            } else {
                player = (EntityPlayerMP) sender;    
            }

         //   applyModifier(server, sender, dragon -> dragon.setWhistleState(state));
        }
		
	}

}
