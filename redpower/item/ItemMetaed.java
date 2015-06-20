package redpower.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public abstract class ItemMetaed extends Item
{
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	/**
	 * Start from 2
	 */
	private int metanum=2; 
	private String iconcatgory;
	public int startIconid;
	public String[] names;
	/**
	 * 
	 * @param id 
	 * @param metanum total number of metaeditems in an id
	 */
	public ItemMetaed(int id,int metanum,String iconcatgory,int startIconid,String nameroot,String[] names)
	{
		super(id);
		this.metanum=metanum;
		this.iconcatgory=iconcatgory;
		this.startIconid=startIconid;
		this.setUnlocalizedName(nameroot);
		this.names=names;
		this.setHasSubtypes(true);
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + names[i];
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int x = 0; x < this.metanum; x++)
		{
			par3List.add(new ItemStack(this, 1, x));
		}
	}
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		icons = new Icon[this.metanum];

		for (int i = 0; i <this.metanum; i++)
		{
			icons[i] = par1IconRegister.registerIcon("redpower" + ":" +this.iconcatgory+"/"+(this.startIconid+ i));
		}
	}
	public Icon getIconFromDamage(int par1)
	{
		return icons[par1];
	}
}
