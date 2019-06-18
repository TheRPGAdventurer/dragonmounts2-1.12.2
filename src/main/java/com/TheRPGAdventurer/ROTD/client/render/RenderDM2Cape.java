package com.TheRPGAdventurer.ROTD.client.render;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class RenderDM2Cape {
    public ResourceLocation suntex = new ResourceLocation(DragonMounts.MODID, "textures/models/misc/sun_cape.png");
    public ResourceLocation icetex = new ResourceLocation(DragonMounts.MODID, "textures/models/misc/ice_cape.png");
    public ResourceLocation stormtex = new ResourceLocation(DragonMounts.MODID, "textures/models/misc/storm_cape.png");
    public ResourceLocation nethertex = new ResourceLocation(DragonMounts.MODID, "textures/models/misc/nether_cape.png");
    public ResourceLocation foresttex = new ResourceLocation(DragonMounts.MODID, "textures/models/misc/forest_cape.png");

    public UUID[] sun = new UUID[] {
      /*GundunUkan */    UUID.fromString("003b050f-f6fd-43b5-9738-669b23c3452f")
    };

    public UUID[] forest = new UUID[] {
      /*me */      UUID.fromString("eb9a02ed-587a-45c7-abaa-4ab28c5eedd4")
    };

    public UUID[] ice = new UUID[] {
      /*Wolf*/      UUID.fromString("1f01c469-70de-4ad3-bc60-deb66db410f2")
    };

    public UUID[] storm = new UUID[] {
      /*Kingdomall*/      UUID.fromString("8a89b0d3-1bb2-431a-94cb-c7e304933176")
    };

    public UUID[] nether = new UUID[] {
      /*Shannieanne*/      UUID.fromString("7d5cbd00-af13-4ae7-b925-edbff61b2c56")
    };

    @SubscribeEvent
    public void playerRender(RenderPlayerEvent.Pre event) {
        if (event.getEntityPlayer() instanceof AbstractClientPlayer) {
            NetworkPlayerInfo info = null;
            try {
                info = (NetworkPlayerInfo) ReflectionHelper.findField(AbstractClientPlayer.class, new String[]{"playerInfo", "field_175157_a"}).get(event.getEntityPlayer());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (info != null) {
                Map<MinecraftProfileTexture.Type, ResourceLocation> textureMap = null;
                try {
                    textureMap = (Map<MinecraftProfileTexture.Type, ResourceLocation>) ReflectionHelper.findField(NetworkPlayerInfo.class, new String[]{"playerTextures", "field_187107_a"}).get(info);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (textureMap != null) {
                    if (SunCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, suntex);
                    }
                    if (ForestCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, foresttex);
                    }
                    if (NetherCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, nethertex);
                    }
                    if (StormCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, stormtex);
                    }
                    if (IceCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, icetex);
                    }
                }
            }
        }
    }

    private boolean SunCape(UUID uniqueID) {
        for (UUID uuid1 : sun) {
            if (uniqueID.equals(uuid1)) {
                return true;
            }
        }
        return false;
    }

    private boolean StormCape(UUID uniqueID) {
        for (UUID uuid1 : storm) {
            if (uniqueID.equals(uuid1)) {
                return true;
            }
        }
        return false;
    }

    private boolean NetherCape(UUID uniqueID) {
        for (UUID uuid1 : nether) {
            if (uniqueID.equals(uuid1)) {
                return true;
            }
        }
        return false;
    }

    private boolean ForestCape(UUID uniqueID) {
        for (UUID uuid1 : forest) {
            if (uniqueID.equals(uuid1)) {
                return true;
            }
        }
        return false;
    }

    private boolean IceCape(UUID uniqueID) {
        for (UUID uuid1 : ice) {
            if (uniqueID.equals(uuid1)) {
                return true;
            }
        }
        return false;
    }
}
