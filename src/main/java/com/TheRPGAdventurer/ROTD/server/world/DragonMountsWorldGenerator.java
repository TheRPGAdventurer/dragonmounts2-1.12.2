package com.TheRPGAdventurer.ROTD.server.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.util.DMUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;


public class DragonMountsWorldGenerator implements IWorldGenerator {
	//@formatter:off

	StructureDragonNests dragonNest = new StructureDragonNests();
	StructureDragonNestSnow dragonNestSnow = new StructureDragonNestSnow();
	StructureDragonNestNether dragonNestNether = new StructureDragonNestNether();
	StructureDragonNestBone dragonNestBone = new StructureDragonNestBone();

	
	//@formatter:on
	@Override
	public void generate(Random random, int x, int z, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
	//	switch(world.provider.getDimension()) {
	//	case -1: //Nether
	//		this.generateNestAtNether(world, random, x, z);
	//		this.generateBoneNestAtNether(world, random, x, z);
	//		break;
	//	case 0: //OverWorld (Earth)
	//		this.generateNestAtSurface(world, random, x, z);
//			this.generateNestUnderground(world, random, x, z);
//			break;
// 	case 1: //End
///			break;
//		}
		
		if(world.provider.getDimensionType() == DimensionType.NETHER) {
			this.generateNestAtNether(world, random, x, z);
		} else if(!isDimensionBlacklisted(world.provider.getDimension())) {
			this.generateNestAtSurface(world, random, x, z);
		} else if(world.provider.getDimensionType() == DimensionType.THE_END) {
			this.generateNestAtEnd(world, random, x, z);
		}
	}
	
	public static BlockPos getHeight(World world, BlockPos pos) {
		return world.getHeight(pos);
	}
	
	private BlockPos getNetherHeight(World world, BlockPos pos){
		for(int i = 0; i < 255; i++){
			BlockPos ground = pos.up(i);
			if(world.getBlockState(ground).getBlock() == Blocks.NETHERRACK && world.isAirBlock(ground.up())){
				return ground;
			}
		}
		return null;
	}
	
	public void generateNestAtSurface(World world, Random random, int chunkX, int chunkZ) {	
		int x = (chunkX * 16) + random.nextInt(16);
		int z = (chunkZ * 16) + random.nextInt(16);
		BlockPos height = getHeight(world, new BlockPos(x, 0, z));	
		
		boolean isHills  = BiomeDictionary.hasType(world.getBiome(height), Type.MOUNTAIN);
		boolean isSnowy  = BiomeDictionary.hasType(world.getBiome(height), Type.SNOWY);
		boolean isJungle = BiomeDictionary.hasType(world.getBiome(height), Type.JUNGLE);
		boolean isForest = BiomeDictionary.hasType(world.getBiome(height), Type.FOREST);
		boolean isSwamp = BiomeDictionary.hasType(world.getBiome(height), Type.SWAMP);
		boolean isDesert = BiomeDictionary.hasType(world.getBiome(height), Type.SANDY);
		boolean isPlains = BiomeDictionary.hasType(world.getBiome(height), Type.PLAINS);
		boolean isMesa   = BiomeDictionary.hasType(world.getBiome(height), Type.MESA);
		
		if (DragonMountsConfig.canSpawnSurfaceDragonNest) {
			if (isHills && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1) {
	 		
	 	 loadStructure(new BlockPos(height.getX(), height.getY() + 80, height.getZ()), world, "aether");
		   DMUtils.getLogger().info("Aether Nest here at: " + new BlockPos(height.getX(), height.getY() + 80, height.getZ()));	
			
	 	} else if(isSnowy && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1 && world.getBlockState(height).getBlock() == Blocks.GRASS) {
	 	 
	 		loadStructure(height, world, "ice");
    DMUtils.getLogger().info("Ice Nest here at: " + height);			
			     
		 } else if(isJungle && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1) {	 	 
				
				loadStructure(height, world, "forest1");
	   DMUtils.getLogger().info("Jungle Nest here at: " + height);
			 
		 } else if(isDesert && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1 && world.getBlockState(height).getBlock() == Blocks.SAND) {	 	 
			
			loadStructure(height, world, "sunlight");
   DMUtils.getLogger().info("Sunlight Nest here at: " + height);
		 
			} else if(isMesa && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1 && world.getBlockState(height).getBlock() == Blocks.SAND) { 	 
				loadStructure(height, world, "terra");
    DMUtils.getLogger().info("Terra Nest here at: " + height);
		
			} else if(isDesert && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1 && world.getBlockState(height).getBlock() == Blocks.SAND) {  
   	loadStructure(height, world, "water1");
    DMUtils.getLogger().info("Water Desert Nest here at: " + height);
		
		 } else if(isSwamp && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1 && world.getBlockState(height).getBlock() == Blocks.GRASS) {	 	 
				loadStructure(height, world, "water2");
    DMUtils.getLogger().info("Water Plains Nest here at: " + height);
		 
		 } else if(isForest && random.nextInt((DragonMountsConfig.MainNestRarity)) == 1 && world.getBlockState(height).getBlock() == Blocks.GRASS) {	 	 
				loadStructure(height, world, "forest2");
    DMUtils.getLogger().info("Forest Nest here at: " + height);
    
		  }
 		}
		}
	
	public void generateNestAtNether(World world, Random random, int chunkX, int chunkZ) {
		if (DragonMountsConfig.canSpawnNetherNest && !world.isRemote) {
			WorldServer worldserver = (WorldServer) world;
			MinecraftServer minecraftserver = world.getMinecraftServer();
			TemplateManager templatemanager = worldserver.getStructureTemplateManager();
			ResourceLocation loc = new ResourceLocation(DragonMounts.MODID, "nether");
     int x = (chunkX * DragonMountsConfig.netherNestRarerityInX) + random.nextInt(DragonMountsConfig.netherNestRarerityInX);
		   int z = (chunkZ * DragonMountsConfig.netherNestRarerityInZ) + random.nextInt(DragonMountsConfig.netherNestRarerityInZ);
		   
		
	 	for (int y = 85; y >= 5; y--) {
	    	boolean solidGround = world.getBlockState(new BlockPos(x,y,z)).isBlockNormalCube();
	    	if (solidGround && random.nextInt(DragonMountsConfig.netherNestRarity) == 1) {
				  	boolean place = true;
				
	 	for(int Y = 0; Y < 7; Y++) {for(int Z = 0; Z < 7; Z++) {for(int X = 0; X < 3; X++) {if(world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock() != Blocks.AIR) {place = false;}}}}
	 	for(int Y = 0; Y < 7; Y++) {for(int Z = 0; Z < 7; Z++) {for(int X = 0; X < 3; X++) {if(world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock() == Blocks.LAVA) {place = false;}}}}
				
				if(place) {
				  int mirror = world.rand.nextInt(Mirror.values().length);
			 	 int rotation = world.rand.nextInt(Rotation.values().length);
			 		loadStructure(new BlockPos(x, y, z), worldserver, "nether");
		   	DMUtils.getLogger().info("Nether Nest here at: " + new BlockPos(x,y,z));
			  	
				 }    				    
			 }
	  }
		}
 }
	
	public void loadStructure(BlockPos pos, World world, String name) {
		WorldServer worldserver = (WorldServer) world;
		MinecraftServer minecraftserver = world.getMinecraftServer();
		TemplateManager templatemanager = worldserver.getStructureTemplateManager();
		ResourceLocation loc = new ResourceLocation(DragonMounts.MODID, name);
		Template template = templatemanager.getTemplate(minecraftserver, loc);
 	int mirror = world.rand.nextInt(Mirror.values().length);
  int rotation = world.rand.nextInt(Rotation.values().length);

		if (template != null) {
			IBlockState iblockstate = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
			PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.values()[mirror])
     .setRotation(Rotation.values()[rotation]).setIgnoreEntities(false).setChunk((ChunkPos) null)
     .setReplacedBlock((Block) null).setIgnoreStructureBlock(true);

			template.addBlocksToWorldChunk(world, pos.add(0, 1, 0), placementsettings);
		} else if(template == null) {
			System.out.println("NO Nest");
		}
	}
	
	public void generateNestAtEnd(World world, Random random, int chunkX, int chunkZ) {
		WorldServer worldserver = (WorldServer) world;
		MinecraftServer minecraftserver = world.getMinecraftServer();
		TemplateManager templatemanager = worldserver.getStructureTemplateManager();
		
		ResourceLocation enchant = new ResourceLocation(DragonMounts.MODID, "enchant");
		Template enchantNest = templatemanager.getTemplate(minecraftserver, enchant); 
		if(DragonMountsConfig.canSpawnEndNest &&  enchantNest != null) {
			int x = (chunkX * 16) + random.nextInt(16);
			int z = (chunkZ * 16) + random.nextInt(16);
			BlockPos height = getHeight(world, new BlockPos(x, 0, z));	
			
			loadStructure(height, worldserver, "enchant");
   DMUtils.getLogger().info("Water Plains Nest here at: " + height);
			
		}
		
	}
	
 public void generateNestUnderground(World world, Random random, int chunkX, int chunkZ) {
if (DragonMountsConfig.canSpawnUnderGroundNest) {
 	boolean spawn = true;
int x = (chunkX * DragonMountsConfig.undergroundnestX) + random.nextInt(DragonMountsConfig.undergroundnestX); 
int z = (chunkZ * DragonMountsConfig.undergroundnestZ) + random.nextInt(DragonMountsConfig.undergroundnestZ); 
  for (int y = 45; y >= 5; --y) {
  if (world.getBlockState(new BlockPos(x,y,z)).getBlock().isAir(world.getBlockState(new BlockPos(x,y,z)), world, new BlockPos(x,y,z))) {
  if((random.nextInt() * DragonMountsConfig.undergroundrarityMain) <= 1) {
for (int y2 = 0; y2 <= 30; ++y2) {
  if (world.getBlockState(new BlockPos(x,y-y2,z)).isBlockNormalCube()) {
                  	 	                    	 
  if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == Blocks.LAVA) {spawn = false;}
  if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock()  == Blocks.OBSIDIAN) {spawn = false;}
  if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock()  == Blocks.COBBLESTONE) {spawn = false;}
  if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock()  == Blocks.MOSSY_COBBLESTONE) {spawn = false;}
        							                                 	         
	             if(spawn) {
	            	if (world.getBlockState(new BlockPos(x,y-y2,z)).isNormalCube()) {
		            dragonNest.generate(world, new BlockPos(x,y-y2,z), random);
		          //  DMUtils.getLogger().info("Underground Nest here at: " + new BlockPos(x,y,z));				            				            
	              } return;
	            }
	          }
	      }
      } break;
    }
   }
 }
}
 
	private boolean isDimensionBlacklisted(int id) {
		boolean useBlackOrWhiteLists = DragonMountsConfig.useDimensionBlackList;
		int[] blacklistedArray =  DragonMountsConfig.dragonBlacklistedDimensions;
		int[] whitelistedArray =  DragonMountsConfig.dragonWhitelistedDimensions;
		int[] array = useBlackOrWhiteLists ? blacklistedArray : whitelistedArray;
		List<Integer> dimList = new ArrayList<Integer>();
		for (int dimension : array) {
			dimList.add(dimension);
		}

		if (dimList.contains(id)) {
			return useBlackOrWhiteLists;
		}
		return !useBlackOrWhiteLists;
	}
}