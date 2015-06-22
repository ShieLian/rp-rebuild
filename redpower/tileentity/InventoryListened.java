package redpower.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryListened implements IInventory
{
	private ItemStack[] stackList;
	private Container eventHandler;
	private int inventoryWidth;
	
	/**
	 * 
	 * @param par1Container callback
	 * @param par2 w
	 * @param par3 h
	 */
	public InventoryListened(Container par1Container, int par2, int par3)
    {
        int k = par2 * par3;
        this.stackList = new ItemStack[k];
        this.eventHandler = par1Container;
        this.inventoryWidth = par2;
    }

	@Override
	public int getSizeInventory()
	{
		return this.stackList.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		 return i >= this.getSizeInventory() ? null : this.stackList[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		// TODO 自动生成的方法存根
		return null;
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
		// TODO 自动生成的方法存根

	}

	@Override
	public String getInvName()
	{
		return "";
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
	public void onInventoryChanged()
	{
		// TODO 自动生成的方法存根
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest()
	{

	}

	@Override
	public void closeChest()
	{

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}

}
