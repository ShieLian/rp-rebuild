package redpower.invventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerBlackHole extends Container
{

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return false;
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		
	}
}
