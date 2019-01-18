package com.TheRPGAdventurer.ROTD.client.tile;

import java.util.UUID;

import com.TheRPGAdventurer.ROTD.client.blocks.BlockDragonSkull;
import com.TheRPGAdventurer.ROTD.server.entity.breeds.EnumDragonBreed;
import com.mojang.authlib.GameProfile;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDragonHead extends TileEntity {
	
    private int skullRotation;
    public EnumDragonBreed breed; 
    
    public TileEntityDragonHead() { 
		super();
	}
	
	public void setBreed(EnumDragonBreed breed) {
		this.breed = breed;
	}
	
	public EnumDragonBreed getBreed() {
		return breed;
	}
	
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("Rot", (byte)(this.skullRotation & 255));

        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.skullRotation = compound.getByte("Rot");
    }
	
    @SideOnly(Side.CLIENT)
    public int getSkullRotation() {
        return this.skullRotation;
    }

    public void setSkullRotation(int rotation)
    {
        this.skullRotation = rotation;
    }

    public void mirror(Mirror mirrorIn)
    {
        if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockDragonSkull.FACING) == EnumFacing.UP)
        {
            this.skullRotation = mirrorIn.mirrorRotation(this.skullRotation, 16);
        }
    }

    public void rotate(Rotation rotationIn)
    {
        if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockDragonSkull.FACING) == EnumFacing.UP)
        {
            this.skullRotation = rotationIn.rotate(this.skullRotation, 16);
        }
    }

}
