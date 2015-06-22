package redpower.invventory;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class SlotResultRefill extends SlotCrafting
{
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;
	private IInventory invRepo;
	private ContainerAdvBench callback;
	/**
	 * @author ShieLian
	 * @param par1EntityPlayer
	 * @param par2iInvMatrix
	 * @param invResult
	 * @param repo InvRepo
	 * @param par4 SlotID
	 * @param par5
	 * @param par6
	 */
	public SlotResultRefill(EntityPlayer par1EntityPlayer,ContainerAdvBench callback,
			IInventory par2iInvMatrix, IInventory invResult,IInventory repo, int par4,
			int par5, int par6)
	{
		super(par1EntityPlayer, par2iInvMatrix, invResult, par4, par5,
				par6);
		this.thePlayer=par1EntityPlayer;
		this.craftMatrix=par2iInvMatrix;
		this.invRepo=repo;
		this.callback =callback;
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        GameRegistry.onItemCrafted(par1EntityPlayer, par2ItemStack, craftMatrix);
        this.onCrafting(par2ItemStack);

		for (/**SlotID*/int i = 0; i <9; ++i)
		{
			ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);
			IInventory tarinv=null;
			int slotid=i;
			if (itemstack1 != null)
			{
				for (int j = 10; j < 27; j++)
				{
					if (this.invRepo.getStackInSlot(j)!=null&&this.invRepo.getStackInSlot(j).isItemEqual(itemstack1))
					{
						this.invRepo.decrStackSize(j, 1);
						this.craftMatrix.decrStackSize(i, 0);
						tarinv=this.invRepo;
						slotid=j;
						break;
					}
				}
                if(tarinv==null)
                {
                	this.craftMatrix.decrStackSize(i, 1);
                	tarinv=this.craftMatrix;
                }

                if (itemstack1.getItem().hasContainerItem())
                {
                    ItemStack itemstack2 = itemstack1.getItem().getContainerItemStack(itemstack1);

                    if (itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage())
                    {
                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, itemstack2));
                        itemstack2 = null;
                    }

                    if (itemstack2 != null && (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !this.thePlayer.inventory.addItemStackToInventory(itemstack2)))
                    {
                        if (tarinv.getStackInSlot(i) == null)
                        {
                        	tarinv.setInventorySlotContents(slotid, itemstack2);
                        }
                        else
                        {
                            this.thePlayer.dropPlayerItem(itemstack2);
                        }
                    }
                }
            }
			else
			{
				callback.consume(i);
			}
        }
    }

}
