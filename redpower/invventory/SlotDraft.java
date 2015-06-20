package redpower.invventory;

import redpower.item.ItemDraft;
import redpower.item.ItemProxy;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDraft extends Slot
{

	public SlotDraft(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}
	@Override
	public boolean isItemValid(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() instanceof ItemDraft;
    }
	@Override
	public int getSlotStackLimit()
    {
        return 1;
    }
}
