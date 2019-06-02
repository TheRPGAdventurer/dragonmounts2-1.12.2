/*
** 2016 April 27
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.cmd;

import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;


import static net.minecraft.command.CommandBase.getCommandSenderAsPlayer;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public interface IDragonModifier {
    
    default void applyModifier(MinecraftServer server, ICommandSender sender, Consumer<EntityTameableDragon> modifier) throws CommandException {
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = getCommandSenderAsPlayer(sender);
            
            // Get the players bounding box and expand it
            AxisAlignedBB aabb = player.getEntityBoundingBox().grow(16, 5, 16); //Modifier Range. Defaults: XZ Plane: 16; Y Plane: 5
            
            // List all dragons in expanded player entity box
            List<EntityTameableDragon> dragons = player
            		.world
            		.getEntitiesWithinAABB(EntityTameableDragon.class, aabb);

            // Get the closest dragon if theres more than 1
            Optional<EntityTameableDragon> closestDragon = dragons
            		.stream()
            		.min( (dragon1, dragon2) -> Float.compare(dragon1.getDistanceToEntity(player), dragon2.getDistanceToEntity(player)) );

            if (!closestDragon.isPresent()) throw new CommandException("commands.dragon.nodragons");
            
            modifier.accept(closestDragon.get());
        } else {
            // scan all entities on all dimensions
            for (WorldServer worldServer : server.worlds) {
                // need a copy of all dragon entities before applying modifier,
                // since it could delete from the server entity list during iteration
                List<EntityTameableDragon> dragons = worldServer.loadedEntityList
                    .stream()
                    .filter(entity -> entity instanceof EntityTameableDragon)
                    .map(entity -> (EntityTameableDragon) entity)
                    .collect(Collectors.toList());
                
                dragons.forEach(modifier);
            }
        }
    }
}
