/*
** 2016 April 24
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server.entity.interact;

import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.entity.helper.DragonHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonInteractHelper extends DragonHelper {
    
    private final List<DragonInteractBase> actions = new ArrayList<>();
    
    public DragonInteractHelper(EntityTameableDragon dragon) {
        super(dragon);
        actions.add(new DragonInteract(dragon));
    }
    
    public boolean interact(EntityPlayer player, ItemStack item) {
        return actions.stream().anyMatch(action -> action.interact(player, item));
    }
}
