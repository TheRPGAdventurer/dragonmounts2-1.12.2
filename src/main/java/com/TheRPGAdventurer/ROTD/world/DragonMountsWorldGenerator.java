package com.TheRPGAdventurer.ROTD.world;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.util.DMUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DragonMountsWorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int x, int z, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimensionType()==DimensionType.NETHER) {
            this.generateNestAtNether(world, random, x, z);
            this.generateZombieAtNether(world, random, x, z);
        } else if (!isDimensionBlacklisted(world.provider.getDimension())) {
            this.generateNestAtSurface(world, random, x, z);
            //			this.generateNestUnderground(world, random, x, z);
        } else if (world.provider.getDimensionType()==DimensionType.THE_END && (x > 2000 || z > 2000 || x < -2000 || z < 2000)) {
            this.generateNestAtEnd(world, random, x, z);
        }
    }

    public static BlockPos getHeight(World world, BlockPos pos) {
        return world.getHeight(pos);
    }

    private BlockPos getEndHeight(World world, BlockPos pos) {
        for (int i=0; i < 255; i++) {
            BlockPos ground=pos.up(i);
            if (world.getBlockState(ground).getMaterial().isSolid() && world.isAirBlock(ground.up())) {
                return ground;
            }
        }
        return null;
    }

    private BlockPos getNetherHeight(World world, BlockPos pos) {
        for (int i=0; i < 255; i++) {
            BlockPos ground=pos.up(i);
            if (world.getBlockState(ground).getBlock()==Blocks.LAVA && world.isAirBlock(ground.up())) {
                return ground;
            }
        }
        return null;
    }

    private BlockPos getCaveHeight(World world, BlockPos pos) {
        for (int i=0; i < 32; i++) {
            BlockPos ground=pos.up(i);
            if (world.getBlockState(ground).getBlock()==Blocks.LAVA && world.isAirBlock(ground.up())) {
                return ground;
            }
        }
        return null;
    }

    private boolean canReplace(World world, BlockPos pos) {
        Material material=world.getBlockState(pos).getMaterial();
        // we think it's replaceable if it's air / liquid / snow, plants, or leaves
        return material.isReplaceable() || material==Material.PLANTS;
    }

    private boolean isSolid(World world, BlockPos pos) {
        Block at=world.getBlockState(pos).getBlock();
        Material material=world.getBlockState(pos).getMaterial();
        return material.isSolid();
    }

    private boolean isLava(World world, BlockPos pos) {
        Block at=world.getBlockState(pos).getBlock();
        return at==Blocks.LAVA;
    }

    private boolean isWater(World world, BlockPos pos) {
        Block at=world.getBlockState(pos).getBlock();
        return at==Blocks.WATER;
    }

    public void generateNestAtSurface(World world, Random random, int chunkX, int chunkZ) {
        int x=(chunkX * 16) + random.nextInt(16);
        int z=(chunkZ * 16) + random.nextInt(16);
        BlockPos height=getHeight(world, new BlockPos(x, 0, z));

        boolean isMountainOrBeach=BiomeDictionary.hasType(world.getBiome(height), Type.MOUNTAIN) || world.getBiomeForCoordsBody(height)==Biomes.STONE_BEACH;
        boolean isSnowy=BiomeDictionary.hasType(world.getBiome(height), Type.SNOWY);
        boolean isJungle=BiomeDictionary.hasType(world.getBiome(height), Type.JUNGLE);
        boolean isForest=BiomeDictionary.hasType(world.getBiome(height), Type.FOREST);
        boolean isSwamp=BiomeDictionary.hasType(world.getBiome(height), Type.SWAMP);
        boolean isDesert=BiomeDictionary.hasType(world.getBiome(height), Type.SANDY);
        boolean isPlains=BiomeDictionary.hasType(world.getBiome(height), Type.PLAINS);
        boolean isMesa=BiomeDictionary.hasType(world.getBiome(height), Type.MESA);
        boolean isOcean=BiomeDictionary.hasType(world.getBiome(height), Type.OCEAN);

        if (DragonMountsConfig.canSpawnSurfaceDragonNest && !world.isRemote) {
            if (isOcean && random.nextInt((DragonMountsConfig.OceanNestRarity))==1) {

                loadStructure(new BlockPos(height.getX(), height.getY() + 10, height.getZ()), world, random.nextBoolean() ? "aether" : "moonlight", LootTableList.CHESTS_WOODLAND_MANSION, true, random);
                //   DMUtils.getLogger().info("Aether Nest here at: " + new BlockPos(height.getX(), height.getY() + 10, height.getZ()));

            } else if (isSnowy && random.nextInt((DragonMountsConfig.IceNestRarity))==1 && canSpawnHere(world, height, 7)) {

                loadStructure(new BlockPos(height.getX(), height.getY() - 2, height.getZ()), world, "ice", LootTableList.CHESTS_STRONGHOLD_CROSSING, true, random);
                DMUtils.getLogger().info("Ice Nest here at: " + new BlockPos(height.getX(), height.getY() - 2, height.getZ()));

            } else if (isJungle && random.nextInt((DragonMountsConfig.JungleNestRarity))==1) {
                loadStructure(height, world, "forest1", LootTableList.CHESTS_STRONGHOLD_LIBRARY, true, random);
                DMUtils.getLogger().info("Jungle Nest here at: " + height);

            } else if (isDesert && random.nextInt((DragonMountsConfig.SunlightNestRarity))==1 && canSpawnHere(world, height, 22)) {

                loadStructure(new BlockPos(height.getX(), height.getY() - 10, height.getZ()), world, "sunlight", LootTableList.CHESTS_DESERT_PYRAMID, true, random);
                DMUtils.getLogger().info("Sunlight Nest here at: " + new BlockPos(height.getX(), height.getY(), height.getZ()));

            } else if (isMesa && random.nextInt((DragonMountsConfig.TerraNestRarity))==1 && canSpawnHere(world, height, 4)) {
                loadStructure(new BlockPos(height.getX(), height.getY() - 2, height.getZ()), world, "terra", LootTableList.CHESTS_NETHER_BRIDGE, true, random);
                DMUtils.getLogger().info("Terra Nest here at: " + new BlockPos(height.getX(), height.getY() - 1, height.getZ()));

            } else if ((isSwamp) && random.nextInt((DragonMountsConfig.WaterNestRarity))==1 && canSpawnWaterHere(world, height, 4)) {
                loadStructure(new BlockPos(height.getX(), height.getY() - 4, height.getZ()), world, "water3", LootTableList.CHESTS_DESERT_PYRAMID, true, random);
                DMUtils.getLogger().info("Water Plains Nest here at: " + new BlockPos(height.getX(), height.getY() - 2, height.getZ()));

            } else if ((isPlains || isForest) && random.nextInt((DragonMountsConfig.ForestNestRarity))==1 && canSpawnHere(world, height, 4)) {
                loadStructure(new BlockPos(height.getX(), height.getY() - 2, height.getZ()), world, "forest2", LootTableList.CHESTS_DESERT_PYRAMID, true, random);

                DMUtils.getLogger().info("Forest Nest here at: " + new BlockPos(height.getX(), height.getY() - 2, height.getZ()));
            } else if (isMountainOrBeach && random.nextInt(DragonMountsConfig.FireNestRarity)==1 && canSpawnHere(world, height, 4)) {
                loadStructure(new BlockPos(height.getX(), height.getY() - 2, height.getZ()), world, "fire", LootTableList.CHESTS_SIMPLE_DUNGEON, true, random);
                //  DMUtils.getLogger().info("Fire Nest here at: " + new BlockPos(height.getX(), height.getY() - 2, height.getZ()));
            }
        }
    }

    private boolean canSpawnHere(World world, BlockPos posAboveGround, int size) {
        // check all the corners to see which ones are replaceable
        boolean corner1Air=canReplace(world, posAboveGround);
        boolean corner2Air=canReplace(world, posAboveGround.add(size, 0, 0));
        boolean corner4Air=canReplace(world, posAboveGround.add(0, 0, size));
        boolean corner3Air=canReplace(world, posAboveGround.add(size, 0, size));
        boolean corner5Air=canReplace(world, posAboveGround.add(-size, 0, 0));
        boolean corner6Air=canReplace(world, posAboveGround.add(0, 0, -size));
        boolean corner7Air=canReplace(world, posAboveGround.add(-size, 0, -size));

        boolean below2Solid=isSolid(world, posAboveGround.add(size, -1, 0));
        boolean below3Solid=isSolid(world, posAboveGround.add(0, -1, size));
        boolean below5Solid=isSolid(world, posAboveGround.add(-size, -1, 0));
        boolean below6Solid=isSolid(world, posAboveGround.add(0, -1, -size));

        // if Y > 20 and all corners pass the test, it's okay to spawn the structure && below7Solid && below4Solid
        return posAboveGround.getY() > 20 && corner1Air && corner2Air && corner3Air && corner4Air && corner5Air && corner6Air && corner7Air && below2Solid && below3Solid && below5Solid && below6Solid;
    }

    private boolean canSpawnWaterHere(World world, BlockPos posAboveGround, int size) {
        // check all the corners to see which ones are replaceable
        boolean corner1Air=canReplace(world, posAboveGround);
        boolean corner2Air=canReplace(world, posAboveGround.add(size, 0, 0));
        boolean corner4Air=canReplace(world, posAboveGround.add(0, 0, size));
        boolean corner3Air=canReplace(world, posAboveGround.add(size, 0, size));
        boolean corner5Air=canReplace(world, posAboveGround.add(-size, 0, 0));
        boolean corner6Air=canReplace(world, posAboveGround.add(0, 0, -size));
        boolean corner7Air=canReplace(world, posAboveGround.add(-size, 0, -size));

        boolean below2Solid=isSolid(world, posAboveGround.add(size, -1, 0));
        boolean below3Solid=isSolid(world, posAboveGround.add(0, -1, size));
        boolean below5Solid=isSolid(world, posAboveGround.add(-size, -1, 0));
        boolean below6Solid=isSolid(world, posAboveGround.add(0, -1, -size));
        boolean below7Solid=!isWater(world, posAboveGround.add(0, -3, -size));

        boolean below2Water=isWater(world, posAboveGround.add(size, -1, 0));
        boolean below3Water=isWater(world, posAboveGround.add(0, -1, size));
        boolean below5Water=isWater(world, posAboveGround.add(-size, -1, 0));
        boolean below6Water=isWater(world, posAboveGround.add(0, -1, -size));

        // if Y > 20 and all corners pass the test, it's okay to spawn the structure && below7Solid && below4Solid
        return posAboveGround.getY() > 20 && corner1Air && corner2Air && corner3Air && corner4Air && corner5Air && corner6Air && corner7Air && ((below2Solid && below3Solid && below5Solid && below6Solid) || (below2Water && below3Water && below5Water && below6Water)) && below7Solid;
    }

    private boolean canSpawnNetherHere(World world, BlockPos posAboveGround, int size) {
        boolean below2Solid=isLava(world, posAboveGround.add(size, -1, 0));
        boolean below3Solid=isLava(world, posAboveGround.add(0, -1, size));
        boolean below5Solid=isLava(world, posAboveGround.add(-size, -1, 0));
        boolean below6Solid=isLava(world, posAboveGround.add(0, -1, -size));

        // if Y > 20 and all corners pass the test, it's okay to spawn the structure && below7Solid && below4Solid
        return posAboveGround.getY() > 20 && below2Solid && below3Solid && below5Solid && below6Solid;
    }

    public void generateNestAtNether(World world, Random random, int chunkX, int chunkZ) {
        if (DragonMountsConfig.canSpawnNetherNest && !world.isRemote) {
            WorldServer worldserver=(WorldServer) world;

            int x=(chunkX * DragonMountsConfig.netherNestRarerityInX) + random.nextInt(DragonMountsConfig.netherNestRarerityInX);
            int z=(chunkZ * DragonMountsConfig.netherNestRarerityInZ) + random.nextInt(DragonMountsConfig.netherNestRarerityInZ);

            if (random.nextInt(DragonMountsConfig.netherNestRarity)==1) {
                BlockPos pos=getNetherHeight(world, new BlockPos(x, 0, z));

                if (pos!=null && canSpawnNetherHere(world, pos, 6)) {
                    loadStructure(new BlockPos(x, pos.getY(), z), worldserver, "nether", LootTableList.CHESTS_NETHER_BRIDGE, true, random);
                    DMUtils.getLogger().info("Nether Nest here at: " + new BlockPos(x, pos.getY(), z));
                }
            }
        }
    }

    public void generateZombieAtNether(World world, Random random, int chunkX, int chunkZ) {
        if (DragonMountsConfig.canSpawnNetherNest && !world.isRemote) {
            WorldServer worldserver=(WorldServer) world;

            int x=(chunkX * DragonMountsConfig.zombieNestRarerityInX) + random.nextInt(DragonMountsConfig.zombieNestRarerityInX);
            int z=(chunkZ * DragonMountsConfig.zombieNestRarerityInZ) + random.nextInt(DragonMountsConfig.zombieNestRarerityInZ);
            for (int y=85; y >= 5; y--) {

                boolean solidGround=world.getBlockState(new BlockPos(x, y, z)).isBlockNormalCube();
                if (solidGround && random.nextInt(DragonMountsConfig.zombieNestRarity)==1) {
                    boolean place=true;

                    for (int Y=0; Y < 3; Y++) {
                        for (int Z=0; Z < 3; Z++) {
                            for (int X=0; X < 3; X++) {
                                if (world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock()!=Blocks.AIR) {
                                    place=false;
                                }
                            }
                        }
                    }

                    for (int Y=0; Y < 3; Y++) {
                        for (int Z=0; Z < 3; Z++) {
                            for (int X=0; X < 3; X++) {
                                if (world.getBlockState(new BlockPos(X + x, Y + y + 1, Z + z)).getBlock()==Blocks.LAVA) {
                                    place=false;
                                }
                            }
                        }
                    }

                    if (place) {
                        if (random.nextBoolean()) {
                            loadStructure(new BlockPos(x, y - 10, z), worldserver, "zombie", LootTableList.CHESTS_NETHER_BRIDGE, true, random);
                            DMUtils.getLogger().info("Zombie Nest here at: " + new BlockPos(x, y, z));
                        } else {
                            loadStructure(new BlockPos(x, y - 10, z), worldserver, "skeleton", LootTableList.CHESTS_NETHER_BRIDGE, true, random);
                            DMUtils.getLogger().info("Skeleton Nest here at: " + new BlockPos(x, y, z));

                        }
                    }
                }
            }
        }
    }

    public void generateNestAtEnd(World world, Random random, int chunkX, int chunkZ) {
        WorldServer worldserver=(WorldServer) world;
        MinecraftServer minecraftserver=world.getMinecraftServer();

        if (DragonMountsConfig.canSpawnEndNest && random.nextInt(DragonMountsConfig.EnchantNestRarity)==1) {
            int x=(chunkX * 16) + random.nextInt(16);
            int z=(chunkZ * 16) + random.nextInt(16);
            BlockPos height=getHeight(world, new BlockPos(x, 0, z));

            if (canSpawnHere(world, height, 5)) {
                loadStructure(new BlockPos(height.getX(), height.getY() - 1, height.getZ()), worldserver, "enchant", LootTableList.CHESTS_END_CITY_TREASURE, true, random);
                DMUtils.getLogger().info("Enchant Nest here at: " + height);
            }
        }
    }

    public void loadStructure(BlockPos pos, World world, String name, ResourceLocation lootTable, boolean hasChest, Random rand) {
        WorldServer worldserver=(WorldServer) world;
        MinecraftServer minecraftserver=world.getMinecraftServer();
        TemplateManager templatemanager=worldserver.getStructureTemplateManager();
        ResourceLocation loc=new ResourceLocation(DragonMounts.MODID, name);
        Template template=templatemanager.getTemplate(minecraftserver, loc);

        if (template!=null) {
            IBlockState iblockstate=world.getBlockState(pos);
            world.notifyBlockUpdate(pos, iblockstate, iblockstate, 2);
            PlacementSettings placementsettings=(new PlacementSettings()).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(true);

            template.addBlocksToWorldChunk(world, pos.add(-1 * template.getSize().getX() / 2, 1, -1 * template.getSize().getZ() / 2), placementsettings);
            putResources(worldserver, lootTable, pos.add(-1 * template.getSize().getX() / 2, 1, -1 * template.getSize().getZ() / 2), template, hasChest, rand);
        } else if (template==null) {
            System.out.println("NO Nest");
        }

    }

    public void putResources(World world, ResourceLocation lootTable, BlockPos structurePosIn, Template structure, boolean hasChest, Random rand) {
        if (hasChest) {
            for (int x=0; x <= structure.getSize().getX(); x++) {
                for (int y=0; y <= structure.getSize().getY(); y++) {
                    for (int z=0; z <= structure.getSize().getZ(); z++) {
                        BlockPos tmp=new BlockPos(structurePosIn.getX() + x, structurePosIn.getY() + y, structurePosIn.getZ() + z);
                        TileEntity tileentity=world.getTileEntity(tmp);
                        if (tileentity instanceof TileEntityChest) {
                            ((TileEntityChest) tileentity).setLootTable(lootTable, rand.nextLong());
                        }
                    }
                }
            }
        }
    }

    private boolean isDimensionBlacklisted(int id) {
        boolean useBlackOrWhiteLists=DragonMountsConfig.useDimensionBlackList;
        int[] blacklistedArray=DragonMountsConfig.dragonBlacklistedDimensions;
        int[] whitelistedArray=DragonMountsConfig.dragonWhitelistedDimensions;
        int[] array=useBlackOrWhiteLists ? blacklistedArray : whitelistedArray;
        List<Integer> dimList=new ArrayList<Integer>();
        for (int dimension : array) {
            dimList.add(dimension);
        }

        if (dimList.contains(id)) {
            return useBlackOrWhiteLists;
        }
        return !useBlackOrWhiteLists;
    }
}