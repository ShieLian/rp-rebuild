package redpower.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import redpower.stuff.RPCreativeTab;

public class ItemColoredBrush extends Item
{
	public int colormeta;
	public ItemColoredBrush(int id)
	{
		super(id);// 16, "base/items1",112,names);
		/*for(int i=0;i<16;i++)
		{
			names[i]=names[i].concat("brush");
		}*/
		//this.setUnlocalizedName("brush");
		this.setCreativeTab(RPCreativeTab.tooltab);
		this.setMaxStackSize(1);
	}

	public static Item[] rigistry(int[] ids)
	{
		ItemColoredBrush[] brushs=new ItemColoredBrush[16];
		for(int i=0;i<16;i++)
		{
			brushs[i]=new ItemColoredBrush(ids[i]);
			brushs[i].setUnlocalizedName(ItemProxy.colorlist[i]+"brush");
			brushs[i].colormeta=i;
		}
		return brushs;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon=par1IconRegister.registerIcon("redpower:base/items1/"+(112+this.colormeta));
	}
}
