package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageDragonInventory implements IMessage {

    public int dragonId;
    public int slot_index;
    public int armor_type;

    public MessageDragonInventory() {
    }

    public MessageDragonInventory(int dragonId, int slot_index, int armor_type) {
        this.dragonId = dragonId;
        this.slot_index = slot_index;
        this.armor_type = armor_type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dragonId = buf.readInt();
        slot_index = buf.readInt();
        armor_type = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dragonId);
        buf.writeInt(slot_index);
        buf.writeInt(armor_type);
    }

    public static class MessageDragonInventoryHandler implements IMessageHandler<MessageDragonInventory, IMessage> {

        @Override
        public IMessage onMessage(MessageDragonInventory message, MessageContext ctx) {
            EntityPlayer player = (ctx.side.isClient() ? Minecraft.getMinecraft().player : ctx.getServerHandler().player);
            Entity entity = player.world.getEntityByID(message.dragonId);
            if (entity instanceof EntityTameableDragon) {
                EntityTameableDragon dragon = (EntityTameableDragon) entity;
                if (message.slot_index == 0) {
                    dragon.setSaddled(message.armor_type == 1);
                }
                if (message.slot_index == 1) {
                    dragon.setChested(message.armor_type == 1);
                }

                if (message.slot_index == 2) {
                    dragon.setArmor(message.armor_type);
                }

                if (message.slot_index == 31) {
                    dragon.setBanner1(dragon.dragonInv.getStackInSlot(31));
                }

                if (message.slot_index == 32) {
                    dragon.setBanner1(dragon.dragonInv.getStackInSlot(32));
                }

                if (message.slot_index == 33) {
                    dragon.setBanner1(dragon.dragonInv.getStackInSlot(33));
                }

                if (message.slot_index == 34) {
                    dragon.setBanner1(dragon.dragonInv.getStackInSlot(34));
                }
            }
            return null;
        }
    }
}