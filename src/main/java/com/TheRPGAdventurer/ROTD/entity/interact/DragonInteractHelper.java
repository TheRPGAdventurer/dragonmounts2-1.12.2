/*
** 2016 April 24
**
** The author disclaims copyright to this source code. In place of
** a legal notice, here is a blessing:
**    May you do good and not evil.
**    May you find forgiveness for yourself and forgive others.
**    May you share freely, never taking more than you give.
 */
<<<<<<< HEAD:src/main/java/com/TheRPGAdventurer/ROTD/entity/interact/DragonInteractHelper.java
package com.TheRPGAdventurer.ROTD.entity.interact;
=======
package com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.interact;

import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.helper.DragonHelper;
>>>>>>> 487f066b... changes:src/main/java/com/TheRPGAdventurer/ROTD/entity/entitytameabledragon/interact/DragonInteractHelper.java

import com.TheRPGAdventurer.ROTD.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.entity.helper.DragonHelper;
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
