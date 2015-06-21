package redpower.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import redpower.stuff.RPCreativeTab;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemDraft extends ItemMetaed
{
	public static String[] names=new String[]{"blankdraft","stuffeddraft"};
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
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(par1ItemStack.getItemDamage()==1)
		{
			String s=(String) par3List.get(0);
			s=EnumChatFormatting.BLUE+s.substring(2);
			par3List.remove(0);
			par3List.add(s);
			
			NBTTagCompound tag=par1ItemStack.getTagCompound();
			NBTTagList itemtags=tag.getTagList("Items");
			//NBTTagCompound resulttag=(NBTTagCompound) tag.getTag("Result");
			HashMap<String,Integer> info=new HashMap<String,Integer>();
			NBTTagCompound temptag=null;ItemStack tempstack=null;
			for(int i=0;i<itemtags.tagCount();i++)
			{
				temptag=(NBTTagCompound) itemtags.tagAt(i);
				tempstack=ItemStack.loadItemStackFromNBT(temptag);
				String displayName = tempstack.getDisplayName();
				if(!info.containsKey(displayName))
				{
					info.put(displayName, 1);
				}
				else
				{
					info.put(displayName,info.get(displayName)+1);
				}
			}
			for(Iterator<String> i$=info.keySet().iterator();i$.hasNext();)
			{
				String key=i$.next();
				int i=info.get(key);
				par3List.add((i==1?"":(i+" × "))+key);
			}
		}
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

