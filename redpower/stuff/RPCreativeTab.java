package redpower.stuff;

import redpower.block.BlockProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RPCreativeTab
{
	public static CreativeTabs machinetab,wiretab,lighttab,covertab,tooltab,itemtab;
	public static void registry() 
	{
		machinetab=
				new CreativeTabs("RedPower|Machine")
					{@Override
		    		 @SideOnly(Side.CLIENT)
				     public ItemStack getIconItemStack()
					 {
				        return new ItemStack(BlockProxy.advbench);
				     }
					};
		wiretab=
				new CreativeTabs("RedPower|Wire&Logic")
					{@Override
					 @SideOnly(Side.CLIENT)
					 public ItemStack getIconItemStack()
					 {
						return new ItemStack(Block.redstoneWire);
					 }
					};
		lighttab=
				new CreativeTabs("RedPower|Lighting")
					{@Override
					 @SideOnly(Side.CLIENT)
					 public ItemStack getIconItemStack()
					 {
						return new ItemStack(Block.redstoneWire);
					 }
					};
		covertab=
				new CreativeTabs("RedPower|Cover")
					{@Override
					 @SideOnly(Side.CLIENT)
					 public ItemStack getIconItemStack()
					 {
						return new ItemStack(Block.redstoneWire);
					 }
					};
		tooltab=
				new CreativeTabs("RedPower|Tools")
					{
						@Override
						@SideOnly(Side.CLIENT)
						public ItemStack getIconItemStack()
						{
							return new ItemStack(Block.redstoneWire);
						}
					};
					LanguageRegistry.instance().addStringLocalization("itemGroup.RedPower|Machine", "en_US", "RedPower|Machine");
					LanguageRegistry.instance().addStringLocalization("itemGroup.RedPower|Machine", "zh_CN", "红石力量|机器");
		itemtab=new CreativeTabs("RedPower|Items")
					{
						@Override
						@SideOnly(Side.CLIENT)
						public ItemStack getIconItemStack()
						{
							return new ItemStack(Block.redstoneWire);
						}
					};
	}
}
