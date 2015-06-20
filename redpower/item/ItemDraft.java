package redpower.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import redpower.stuff.RPCreativeTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemDraft extends ItemMetaed
{
	public static String[] names=new String[]{"blanckdraft","stuffeddraft"};
	@SideOnly(Side.CLIENT)
	private static Icon iconblank,iconstuff;
	public ItemDraft(int par1)
	{
		super(par1,2,"base/items1",81,"draft",names);
		this.setMaxStackSize(1);
		this.setCreativeTab(RPCreativeTab.itemtab);
		this.setUnlocalizedName("draft");
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(par1ItemStack.getItemDamage()==1&&par3EntityPlayer.isSneaking())
			return new ItemStack(this,1,0);
		else return par1ItemStack;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(this,1,0));
	}
	
}

