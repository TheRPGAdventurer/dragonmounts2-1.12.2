package com.TheRPGAdventurer.ROTD.network;

import com.TheRPGAdventurer.ROTD.client.gui.GuiDragon;
import com.TheRPGAdventurer.ROTD.entity.entitytameabledragon.EntityTameableDragon;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageDragonWandStats extends AbstractMessage<MessageDragonWandStats> {

    private int dragonId;
    private int guiId;

    public MessageDragonWandStats(int dragonId, int guiId) {
        this.dragonId = dragonId;
        this.guiId = guiId;
    }

    public MessageDragonWandStats() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.guiId = buf.readInt();
        this.dragonId = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.dragonId);
        buf.writeByte(this.guiId);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientReceived(Minecraft mc, MessageDragonWandStats message, EntityPlayer player, MessageContext context) {
        if (mc.currentScreen == null) {
            final EntityTameableDragon entity = (EntityTameableDragon) player.world.getEntityByID(message.dragonId);

            mc.displayGuiScreen(new GuiDragon(player.inventory, entity));

        }
    }

    @Override
    public void onServerReceived(MinecraftServer arg0, MessageDragonWandStats arg1, EntityPlayer arg2,
                                 MessageContext arg3) {

    }

}
