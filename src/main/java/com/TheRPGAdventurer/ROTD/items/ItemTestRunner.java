package com.TheRPGAdventurer.ROTD.items;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.inits.ModItems;
import com.TheRPGAdventurer.ROTD.util.IHasModel;
import com.TheRPGAdventurer.ROTD.util.debugging.TestRunner;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * User: The Grey Ghost
 * Date: 04/01/2016
 *
 * ItemTestRunner is used to trigger a test case
 */
public class ItemTestRunner extends Item implements IHasModel
{
  public ItemTestRunner()
  {
    final int MAX_TEST_NUMBER = 64;
    this.setTranslationKey("test_runner");
    this.setRegistryName(new ResourceLocation(DragonMounts.MODID, "test_runner"));

    this.setMaxStackSize(MAX_TEST_NUMBER);

    if (DragonMounts.instance.getConfig().isDebug()) {
      this.setCreativeTab(DragonMounts.mainTab);
      ModItems.ITEMS.add(this);
    }
  }

  /**
   * allows items to add custom lines of information to the mouseover description
   */
  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
  {
    tooltip.add("Right click: conduct test");
    tooltip.add("Stacksize: change test #");
    tooltip.add("  (64 = test all)");
  }

  // what animation to use when the player holds the "use" button
  @Override
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BLOCK;
  }

  // how long the player needs to hold down the right button before the test runs again
  @Override
  public int getMaxItemUseDuration(ItemStack stack) {
    return 20;
  }

  // called when the player starts holding right click;
  // called on the client and again on the server
  // execute your test code on the appropriate side....
  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    ItemStack itemStackIn = playerIn.getHeldItem(hand);
    if (itemStackIn.isEmpty()) {  // returns true if the item is empty (player is holding nothing)
      return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);  // just in case.
    }
    int testNumber = itemStackIn.getCount(); // getStackSize()
    TestRunner testRunner = new TestRunner();

    if (worldIn.isRemote) {
      testRunner.runClientSideTest(worldIn, playerIn, testNumber);
    } else {
      testRunner.runServerSideTest(worldIn, playerIn, testNumber);
    }
    return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
  }

  @Override
  public void RegisterModels()
  { DragonMounts.proxy.registerItemRenderer(this, 0, "inventory"); }

}
