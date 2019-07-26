package com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.breeds;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.EnumAction;
import net.minecraft.util.IStringSerializable;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public enum EnumDragonBreed implements IStringSerializable {

    AETHER(0, DragonBreedAir::new),
    FIRE(1, DragonBreedFire::new),
    FOREST(2, DragonBreedForest::new),
    SYLPHID(3, DragonBreedWater::new),
    ICE(4, DragonBreedIce::new),
    END(5, DragonBreedEnd::new),
    NETHER(6, DragonBreedNether::new),
    SKELETON(7, DragonBreedSkeleton::new),
    WITHER(8, DragonBreedWither::new),
    ENCHANT(9, DragonBreedEnchant::new),
    SUNLIGHT(10, DragonBreedSunlight::new),
    STORM(11, DragonBreedStorm::new),
    ZOMBIE(12, DragonBreedZombie::new),
    TERRA(13, DragonBreedTerra::new),
    MOONLIGHT(14, DragonBreedMoonlight::new);
//	LIGHT(15, DragonBreedLight::new);
//	DARK(16, DragonBreedDark::new);
//	SPECTER(17, DragonBreedSpecter::new);


    // create static bimap between enums and meta data for faster and easier
    // lookups
    public static final BiMap<EnumDragonBreed, Integer> META_MAPPING = ImmutableBiMap.copyOf(Arrays.asList(values()).stream().collect(Collectors.toMap(Function.identity(), EnumDragonBreed::getMeta)));
    public static final PropertyEnum<EnumDragonBreed> BREED = PropertyEnum.create("breed", EnumDragonBreed.class);
    private final DragonBreed breed;

    // this field is used for block metadata and is technically the same as
    // ordinal(), but it is saved separately to make sure the values stay
    // constant after adding more breeds in unexpected orders
    private final int meta;

    EnumDragonBreed(int meta, Supplier<DragonBreed> factory) {
        this.breed = factory.get();
        this.meta = meta;
    }


    public DragonBreed getBreed() {
        return breed;
    }

    public int getMeta() {
        return meta;
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public int getNumberOfNeckSegments() {
        return 7;
    }

    public int getNumberOfTailSegments() {
        return 12;
    }

    public int getNumberOfWingFingers() {
        return 4;
    }

}

