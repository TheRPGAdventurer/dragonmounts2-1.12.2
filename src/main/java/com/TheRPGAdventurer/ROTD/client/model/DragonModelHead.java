package com.TheRPGAdventurer.ROTD.client.model;

import com.TheRPGAdventurer.ROTD.client.model.dragon.DragonModelMode;
import com.TheRPGAdventurer.ROTD.client.model.dragon.ModelPart;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.TheRPGAdventurer.ROTD.util.math.MathX;

import net.minecraft.client.model.ModelBase;

public class DragonModelHead extends ModelBase {
	
    public ModelPart head;
    public ModelPart jaw;
    
    public static final int HEAD_OFS = -16;
	
    public DragonModelHead() {
        textureWidth = 256;
        textureHeight = 256;
        
     //   this.breed = breed;
        
        setTextureOffset("head.nostril", 48, 0);
        setTextureOffset("head.mainhead", 0, 0);
        setTextureOffset("head.upperjaw", 56, 88);
        setTextureOffset("head.lowerjaw", 0, 88);
        setTextureOffset("head.horn", 28, 32);
       
        
        buildHead();
    }
    
    private void buildHead() {
        head = new ModelPart(this, "head");
        head.addBox("upperjaw",  -6, -1,   -8 + HEAD_OFS, 12,  5, 16);
        head.addBox("mainhead", -8, -8,    5 + HEAD_OFS, 16, 16, 16); // 6
        head.addBox("nostril",   -5, -3,   -6 + HEAD_OFS,  2,  2,  4);
        head.mirror = true;
        head.addBox("nostril",    3,  -3,  -6 + HEAD_OFS,  2,  2,  4);
        
        buildHorn(false);
        buildHorn(true);

        jaw = head.addChildBox("lowerjaw", -6, 0, -16, 12, 4, 16);
        jaw.setRotationPoint(0, 4, 8 + HEAD_OFS);
    }
    
    private void buildHorn(boolean mirror) {
        int hornThick = 3;
        int hornLength = 12;
        
        float hornOfs = -(hornThick / 2f);
        
        float hornPosX = -5;
        float hornPosY = -8;
        float hornPosZ = 0;
        
        float hornRotX = MathX.toRadians(30);
        float hornRotY = MathX.toRadians(-30);
        float hornRotZ = 0;
        
        if (mirror) {
            hornPosX *= -1;
            hornRotY *= -1;
        }
        
        head.mirror = mirror;
        ModelPart horn = head.addChildBox("horn", hornOfs, hornOfs, hornOfs, hornThick, hornThick, hornLength);
        horn.setRotationPoint(hornPosX, hornPosY, hornPosZ);
        horn.setAngles(hornRotX, hornRotY, hornRotZ);
        
    }

}
