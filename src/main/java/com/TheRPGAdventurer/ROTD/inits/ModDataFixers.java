package com.TheRPGAdventurer.ROTD.inits;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.datafix.DragonMountsBlockDataFixes;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ModDataFixers {

    /**
     * The current data version.
     */
    private static final int DATA_VERSION = 1;

    /**
     * Register this mod's data fixers.
     */
    public static void registerDataFixers() {
        final ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(DragonMounts.MODID, DATA_VERSION);

        modFixs.registerFix(FixTypes.ITEM_INSTANCE, new DragonMountsBlockDataFixes());
    }
}
