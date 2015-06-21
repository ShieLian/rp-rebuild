package redpower.invventory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import redpower.tileentity.TileAdvBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerAdvBench extends Container
{
	private TileAdvBench tile;
	private InventoryCrafting matrix=new InventoryCrafting(this, 3, 3);
	private IInventory craftResult = new InventoryCraftResult();
	public boolean hasresult=this.craftResult.getStackInSlot(0)!=null;
	private boolean cancraft=false;
	
	public ContainerAdvBench(InventoryPlayer par1InventoryPlayer,TileAdvBench par2tileAdvbench)
	{
		this.tile = par2tileAdvbench;
		for (int i = 0; i < 9; i++)
		{
			matrix.setInventorySlotContents(i,this.tile.getStackInSlot(i+1));
		}
		//craft:48,18
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				//addSlotToContainer(new Slot(tileEntity, j + i * 3, 62 + j * 18, 17 + i * 18));
				//1-9
				addSlotToContainer(new Slot(this.matrix,x+y*3,48+x*18,18+y*18));
			}
		}
		//benchinv 8,90
		//10-27
		for(int x=0;x<9;x++)
		{
			addSlotToContainer(new Slot(tile,x+10,8+x*18,90));
			addSlotToContainer(new Slot(tile,x+19,8+x*18,108));
		}
		//0
		addSlotToContainer(new SlotDraft(tile,0,17,36));
		//0-35
		bindPlayerInventory(par1InventoryPlayer);
		
		//
		this.addSlotToContainer(new SlotResultRefill(par1InventoryPlayer.player, this.matrix, this.craftResult,this.tile, 28, 143, 36));
		this.onCraftMatrixChanged(this.matrix);
		hasresult=this.craftResult.getStackInSlot(0)!=null;
	}
	
	//0-35
	public void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		//player inv 8,140
		//9-35
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, x+9*y+9,8 + x * 18, 140 + y * 18));
			}
		}
		//0-8
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 198));
		}
	}
	
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		 ItemStack stack = CraftingManager.getInstance().findMatchingRecipe(this.matrix, this.tile.worldObj);
		 //this.craftResult.setInventorySlotContents(0, ItemStack.areItemStacksEqual(stack, new ItemStack( Block.planks, 2, 0)) ? null : stack);
		 this.craftResult.setInventorySlotContents(0, stack);
		 this.hasresult=this.craftResult.getStackInSlot(0)!=null;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		if (!this.tile.worldObj.isRemote)
		{
			for (int i = 0; i < 9; ++i)
			{
				ItemStack var3 = this.matrix.getStackInSlotOnClosing(i);
				this.tile.setInventorySlotContents(i + 1, var3);
			}
		}
	}
	//?
	public void saveMatrix()
	{
		for (int i = 0; i < 9; ++i)
		{
			ItemStack var3 = this.matrix.getStackInSlot(i);
			this.tile.setInventorySlotContents(i + 1, var3);
		}
	}
	
	public NBTTagCompound getResultNBT()
	{
		NBTTagCompound tag=new NBTTagCompound();
		this.craftResult.getStackInSlot(0).writeToNBT(tag);
		return tag;
	}
	
	@Override
    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		System.out.println("Shifted");
		return null;
	}
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	public static ItemStack[] getShadowItems(ItemStack draft)
	{
		// TODO 自动生成的方法存根
		if (draft.stackTagCompound == null)
        {
            return null;
        }
        else
        {
            NBTTagList itemtags = draft.stackTagCompound.getTagList("Items");

            if (itemtags == null)
            {
                return null;
            }
            else
            {
                ItemStack[] result = new ItemStack[9];
                NBTTagCompound temptag;ItemStack tempstack;
                for (int i = 0; i < itemtags.tagCount(); ++i)
                {
                    temptag = (NBTTagCompound)itemtags.tagAt(i);
                    tempstack= ItemStack.loadItemStackFromNBT(temptag);
                    byte slot = temptag.getByte("Slot");
//                    System.out.println(slot+":"+tempstack.getDisplayName());
                    if (slot >0 && slot <=9)
                    {
                        result[slot-1] = tempstack;
                    }
                }

                return result;
            }
        }
	}

	public ItemStack getMatrixStack(int index)
	{
		return this.matrix.getStackInSlot(index);
	}

	public int getRepoNum(ItemStack tar)
	{
		int result=0;
//		HashMap<String,Integer> re=new HashMap<String,Integer>();
//		for(Iterator<String> i$=list.iterator();i$.hasNext();)
//		{
			for(int i=1;i<28;i++)
			{
				if(OreDictionary.itemMatches(this.tile.getStackInSlot(i),tar,false))
				{
					result+=this.tile.getStackInSlot(i).stackSize;
				}
			}
//		}
		return result;
	}
	
	/**
	 * 
	 * @param index
	 * @param tarstack
	 * @param counter 
	 * @return -1 if none. Else means the num
	 */
	public int satisfy(int index, ItemStack tarstack, HashMap<String, Integer> counter)
	{
		// TODO 自动生成的方法存根
		int keynum=OreDictionary.getOreID(tarstack);
		String key=keynum==-1?tarstack.getDisplayName():String.valueOf(keynum);
		if(counter.get(key)<=0) return 0;
		int srcid=index+1;
		int result=0;
		for(int i=1;i<10;i++)
		{
			if(itemMatches(this.tile.getStackInSlot(i),tarstack))
			{
				result+=this.tile.getStackInSlot(i).stackSize;
			}
		}
		for(int i=10;i<28;i++)
		{
			if(itemMatches(this.tile.getStackInSlot(i),tarstack))
			{
				result+=this.tile.getStackInSlot(i).stackSize;
			}
		}
		if(result<counter.get(key)) return -1;
		else
		{
			counter.put(key, counter.get(key)+1);
			return result;
		}
	}
	
	public boolean itemMatches(ItemStack tar,ItemStack input)
	{
		/*ItemStack draft=this.tile.getStackInSlot(0);
		ItemStack[] items=getShadowItems(draft);*/
		boolean flag=OreDictionary.itemMatches(tar, input, false);
		if(flag) return true;
		else
		{
			InventoryCrafting buffer=new InventoryCrafting(this,3,3);
			for(int i=0;i<9;++i)
			{
				buffer.setInventorySlotContents(i,?);
			}
			buffer.setInventorySlotContents(par1, par2ItemStack);
		}
	}
	
	public void setResultByDraft()
	{
		ItemStack result= ItemStack.loadItemStackFromNBT(this.tile.getStackInSlot(0).stackTagCompound.getCompoundTag("Result"));
		result.stackSize=1;
		this.craftResult.setInventorySlotContents(0,result);
	}
}
