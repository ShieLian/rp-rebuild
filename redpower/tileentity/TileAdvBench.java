package redpower.tileentity;

import redpower.invventory.ContainerAdvBench;
import redpower.item.ItemDraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileAdvBench extends TileEntity implements IInventory//,ISidedInventory
{
	private String tilename;
	/**0*/
	private ItemStack draftstack;
	/**1~9*/
	private ItemStack[] craftstack=new ItemStack[9];
	/**10~27*/
	private ItemStack[] invstack=new ItemStack[18];
	/**1~9*/
	private static final int[] craft=new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
	/**10~27*/
	private static final int[] inv = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27 };

	private ContainerAdvBench callback=null;
	
	public TileAdvBench()
	{
		
	}
/*
	@Override
	public int[] getAccessibleSlotsFromSide(int var1)
	{
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j)
	{
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j)
	{
		// TODO 自动生成的方法存根
		return false;
	}
*/
	@Override
	public int getSizeInventory()
	{
		return 18;
	}

	/**
	 *  draft:0 
	 *  CraftMatrix:1~9 
	 *  inv:10~27 
	 */
	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO 自动生成的方法存根
		if(i==0) return this.draftstack;
		else
		{
			if(i<10) return this.craftstack[i-1];
			else return this.invstack[i-10];
		}
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		// TODO 自动生成的方法存根
		if (par2 != 0)
		{
			if (this.getStackInSlot(par1) != null)
			{
				ItemStack itemstack;
				if (this.getStackInSlot(par1).stackSize <= par2)
				{
					itemstack =this.getStackInSlot(par1);
					this.setInventorySlotContents(par1, null);;
					this.onInventoryChanged();
					return itemstack;
				} else
				{
					itemstack =this.getStackInSlot(par1).splitStack(par2);

					if (this.getStackInSlot(par1).stackSize == 0)
					{
						this.setInventorySlotContents(par1, null);
					}

					this.onInventoryChanged();
					return itemstack;
				}
			}
			else return null;
		}
		else return null;
	}
	
	@Override
	 public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i <=27; i++)
        {
            if (this.getStackInSlot(i)!= null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.getStackInSlot(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

        if (this.isInvNameLocalized())
        {
            par1NBTTagCompound.setString("CustomName", this.tilename);
        }
    }
	
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        for (int i = 0;i<nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte slotid = nbttagcompound1.getByte("Slot");

            if (slotid >= 0 && slotid<=28)
            {
                this.setInventorySlotContents(slotid,ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.tilename = par1NBTTagCompound.getString("CustomName");
        }
    }
	
	@Override
 	public ItemStack getStackInSlotOnClosing(int i)
	{
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(i==0)
		{
			if(itemstack==null||itemstack.getItem() instanceof ItemDraft)
				this.draftstack=itemstack;
		}
		else
		{
			if(i<=9)
			{
				this.craftstack[i-1]=itemstack;
			}
			else
			{
				if(i!=28)this.invstack[i-10]=itemstack;
			}
		}
	}

	@Override
	public String getInvName()
	{
		return "tileentity.advbench.name";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		// TODO 自动生成的方法存根
		return true;
	}

	@Override
	public void openChest(){}

	@Override
	public void closeChest(){}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return ((i!=0&&i!=28)||( i==0 && itemstack.getItem() instanceof ItemDraft));
	}
	
	@Override
	public void onInventoryChanged()
    {
        super.onInventoryChanged();
        if(this.callback==null) return;
        else
        {
        	callback.getsatisfyMask();
        }
    }
	
	public void setcallback(ContainerAdvBench callback)
	{
		this.callback=callback;
	}
}