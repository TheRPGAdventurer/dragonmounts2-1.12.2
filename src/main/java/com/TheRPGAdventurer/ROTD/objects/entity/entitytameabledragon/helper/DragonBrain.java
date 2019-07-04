/*
 ** 2016 March 12
 **
 ** The author disclaims copyright to this source code. In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.helper;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.*;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.air.EntityAIDragonFlight;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.air.EntityAIDragonFollowOwnerElytraFlying;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.ai.ground.*;
import com.TheRPGAdventurer.ROTD.util.EntityClassPredicate;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonBrain extends DragonHelper {

    // mutex 1: movement
    // mutex 2: looking
    // mutex 4: special state
    private final EntityAITasks tasks;

    // mutex 1: waypointing
    // mutex 2: continuous waypointing

    // mutex 1: generic targeting
    private final EntityAITasks targetTasks;

    public DragonBrain(EntityTameableDragon dragon) {
        super(dragon);
        tasks = dragon.tasks;
        targetTasks = dragon.targetTasks;
    }

    // public void clearTasks() {
    //     clearTasks(tasks);
    //     clearTasks(targetTasks);
    // }

    //  public void clearTasks(EntityAITasks tasks) {
    //      List<EntityAITaskEntry> taskEntries = new ArrayList<>(tasks.taskEntries);
    //      taskEntries.forEach(entry -> tasks.removeTask(entry.action));
    //  }


    public void setAvoidsWater(boolean avoidWater) {
        PathNavigate pathNavigate = dragon.getNavigator();
        if (pathNavigate instanceof PathNavigateGround) {
            PathNavigateGround pathNavigateGround = (PathNavigateGround) pathNavigate;
            pathNavigateGround.setCanSwim(!avoidWater); // originally setAvoidsWater()
        }
    }

    public void updateAITasks() {
        // only hatchlings are small enough for doors
        // (eggs don't move on their own anyway and are ignored)
        // guessed, based on EntityAIRestrictOpenDoor - break the door down, don't open it
        if (dragon.getNavigator() instanceof PathNavigateGround) {
            PathNavigateGround pathNavigateGround = (PathNavigateGround) dragon.getNavigator();
            pathNavigateGround.setEnterDoors(dragon.isBaby());
        }

        // clear current navigation target
        dragon.getNavigator().clearPath();

        // clear existing tasks
        //     clearTasks();

        // eggs don't have any tasks
        if (dragon.isEgg()) {
            return;
        }

        tasks.addTask(0, new EntityAIDragonCatchOwner(dragon)); // mutex all
        tasks.addTask(1, new EntityAIDragonPlayerControl(dragon)); // mutex all
        tasks.addTask(2, dragon.getAISit()); // mutex 4+1
        tasks.addTask(3, new EntityAIDragonWhistle(dragon)); // mutex all
        tasks.addTask(3, new EntityAIDragonFollowOwnerElytraFlying(dragon)); // mutex all
        tasks.addTask(4, new EntityAIMoveTowardsRestriction(dragon, 1)); // mutex 1

            tasks.addTask(6, new EntityAIDragonFlight(dragon, 1)); // mutex 1
        tasks.addTask(2, new EntityAISwimming(dragon)); // mutex 4
        tasks.addTask(7, new EntityAIAttackMelee(dragon, 1, true)); // mutex 2+1
        tasks.addTask(9, new EntityAIDragonFollowOwner(dragon, 1, 14, 128)); // mutex 2+1
        tasks.addTask(9, new EntityAIDragonFollowOwnerElytraFlying(dragon)); // mutex 2+1
        tasks.addTask(10, new EntityAIWander(dragon, 1)); // mutex 1
        tasks.addTask(11, new EntityAIDragonWatchIdle(dragon)); // mutex 2
        tasks.addTask(11, new EntityAIDragonWatchLiving(dragon, 16, 0.05f)); // mutex 2


        targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntityLiving>(dragon, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>() {
            public boolean apply(@Nullable EntityLiving p_apply_1_) {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_);
            }
        }));
        targetTasks.addTask(5, new EntityAIDragonHunt(dragon, EntityAnimal.class, false, new EntityClassPredicate(EntitySheep.class, EntityPig.class, EntityChicken.class, EntityRabbit.class, EntityLlama.class))); // mutex 1

        if (dragon.isBaby() && dragon.onGround) {
            tasks.addTask(5, new EntityAILeapAtTarget(dragon, 0.7F)); // mutex 1
            tasks.addTask(6, new EntityAIDragonFollowParent(dragon, 1.4f));
            tasks.addTask(6, new EntityAITempt(dragon, 0.75, false, OreDictionary.getOres("listAllfishraw").stream().map(ItemStack::getItem).collect(Collectors.toSet()))); // mutex 2+1
        }

        targetTasks.addTask(2, new EntityAIOwnerHurtByTarget(dragon)); // mutex 1
        targetTasks.addTask(3, new EntityAIOwnerHurtTarget(dragon)); // mutex 1
        targetTasks.addTask(4, new EntityAIDragonHurtByTarget(dragon, false)); // mutex 1

        if (dragon.isAdult()) {
            tasks.addTask(5, new EntityAIDragonMate(dragon, 0.6)); // mutex 2+1
        }
    }
}

