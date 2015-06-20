package redpower.media;

import redpower.invventory.SlotDraft;
import redpower.media.packet.Packet212GuiEvent;
import redpower.gui.IHandleGuiEvent;

import com.eloraam.redpower.core.CoreLib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ContainerAdvBench extends Container implements IHandleGuiEvent
{
    SlotDraft slotDraft;
    private TileAdvBench tileAdvBench;
    public InventorySubCraft craftMatrix;
    public IInventory craftResult;
    public InventoryCrafting fakeInv;
    public int satisfyMask;

    public ContainerAdvBench(InventoryPlayer var1, TileAdvBench var2)
    {
        this.tileAdvBench = var2;
        this.craftMatrix = new InventorySubCraft(this, var2);
        this.craftResult = new InventoryCraftResult();
        int var3;
        int var4;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 3; ++var4)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, var4 + var3 * 3, 48 + var4 * 18, 18 + var3 * 18));
            }
        }

        this.addSlotToContainer(new SlotDraft(new ContainerAdvBench$InventorySubUpdate(this, var2, 9, 1), 0, 17, 36));
        this.slotDraft = new slotCraftRefill(var1.player, this.craftMatrix, this.craftResult, var2, this, 0, 143, 36);
        this.addSlotToContainer(this.slotDraft);
        ContainerAdvBench$InventorySubUpdate var5 = new ContainerAdvBench$InventorySubUpdate(this, var2, 10, 18);

        for (var3 = 0; var3 < 2; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var5, var4 + var3 * 9, 8 + var4 * 18, 90 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 140 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(var1, var3, 8 + var3 * 18, 198));
        }

        this.fakeInv = new InventoryCrafting(new ContainerAdvBench$ContainerNull(), 3, 3);
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * args: slotID, itemStack to put in slot
     */
    public void putStackInSlot(int var1, ItemStack var2)
    {
        super.putStackInSlot(var1, var2);
    }

    public static ItemStack[] getShadowItems(ItemStack var0)
    {
        if (var0.stackTagCompound == null)
        {
            return null;
        }
        else
        {
            NBTTagList var1 = var0.stackTagCompound.getTagList("requires");

            if (var1 == null)
            {
                return null;
            }
            else
            {
                ItemStack[] var2 = new ItemStack[9];

                for (int var3 = 0; var3 < var1.tagCount(); ++var3)
                {
                    NBTTagCompound var4 = (NBTTagCompound)var1.tagAt(var3);
                    ItemStack var5 = ItemStack.loadItemStackFromNBT(var4);
                    byte var6 = var4.getByte("Slot");

                    if (var6 >= 0 && var6 < 9)
                    {
                        var2[var6] = var5;
                    }
                }

                return var2;
            }
        }
    }

    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.tileAdvBench.isUseableByPlayer(var1);
    }

    public ItemStack[] getPlanItems()
    {
        ItemStack var1 = this.tileAdvBench.getStackInSlot(9);
        return var1 == null ? null : getShadowItems(var1);
    }

    public int getSatisfyMask()
    {
        ItemStack var1 = this.tileAdvBench.getStackInSlot(9);
        ItemStack[] var2 = null;

        if (var1 != null)
        {
            var2 = getShadowItems(var1);
        }

        int var3 = 0;
        int var4;
        ItemStack var5;

        for (var4 = 0; var4 < 9; ++var4)
        {
            var5 = this.tileAdvBench.getStackInSlot(var4);

            if (var5 != null)
            {
                var3 |= 1 << var4;
            }
            else if (var2 == null || var2[var4] == null)
            {
                var3 |= 1 << var4;
            }
        }

        if (var3 == 511)
        {
            return 511;
        }
        else
        {
            for (var4 = 0; var4 < 18; ++var4)
            {
                var5 = this.tileAdvBench.getStackInSlot(10 + var4);

                if (var5 != null && var5.stackSize != 0)
                {
                    int var6 = var5.stackSize;

                    for (int var7 = 0; var7 < 9; ++var7)
                    {
                        if ((var3 & 1 << var7) <= 0)
                        {
                            ItemStack var8 = this.tileAdvBench.getStackInSlot(var7);

                            if (var8 == null)
                            {
                                var8 = var2[var7];

                                if (var8 != null && CoreLib.matchItemStackOre(var8, var5))
                                {
                                    var3 |= 1 << var7;
                                    --var6;

                                    if (var6 == 0)
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return var3;
        }
    }

    private int findMatch(ItemStack var1)
    {
        for (int var2 = 0; var2 < 18; ++var2)
        {
            ItemStack var3 = this.tileAdvBench.getStackInSlot(10 + var2);

            if (var3 != null && var3.stackSize != 0 && CoreLib.matchItemStackOre(var1, var3))
            {
                return 10 + var2;
            }
        }

        return -1;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory var1)
    {
        ItemStack var2 = this.tileAdvBench.getStackInSlot(9);
        ItemStack[] var3 = null;

        if (var2 != null)
        {
            var3 = getShadowItems(var2);
        }

        for (int var4 = 0; var4 < 9; ++var4)
        {
            ItemStack var5 = this.tileAdvBench.getStackInSlot(var4);

            if (var5 == null && var3 != null && var3[var4] != null)
            {
                int var6 = this.findMatch(var3[var4]);

                if (var6 > 0)
                {
                    var5 = this.tileAdvBench.getStackInSlot(var6);
                }
            }

            this.fakeInv.setInventorySlotContents(var4, var5);
        }

        this.satisfyMask = this.getSatisfyMask();

        if (this.satisfyMask == 511)
        {
            this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.fakeInv, this.tileAdvBench.worldObj));
        }
        else
        {
            this.craftResult.setInventorySlotContents(0, (ItemStack)null);
        }
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer var1, int var2)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(var2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (var2 == 10)
            {
                this.mergeCrafting(var1, var4, 29, 65);
                return null;
            }

            if (var2 < 9)
            {
                if (!this.mergeItemStack(var5, 11, 29, false))
                {
                    return null;
                }
            }
            else if (var2 < 29)
            {
                if (!this.mergeItemStack(var5, 29, 65, true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 11, 29, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(var1, var5);
        }

        return var3;
    }

    protected boolean canFit(ItemStack var1, int var2, int var3)
    {
        int var4 = 0;

        for (int var5 = var2; var5 < var3; ++var5)
        {
            Slot var6 = (Slot)this.inventorySlots.get(var5);
            ItemStack var7 = var6.getStack();

            if (var7 == null)
            {
                return true;
            }

            if (CoreLib.compareItemStack(var7, var1) == 0)
            {
                var4 += var7.getMaxStackSize() - var7.stackSize;

                if (var4 >= var1.stackSize)
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected void fitItem(ItemStack var1, int var2, int var3)
    {
        int var4;
        Slot var5;
        ItemStack var6;

        if (var1.isStackable())
        {
            for (var4 = var2; var4 < var3; ++var4)
            {
                var5 = (Slot)this.inventorySlots.get(var4);
                var6 = var5.getStack();

                if (var6 != null && CoreLib.compareItemStack(var6, var1) == 0)
                {
                    int var7 = Math.min(var1.stackSize, var1.getMaxStackSize() - var6.stackSize);

                    if (var7 != 0)
                    {
                        var1.stackSize -= var7;
                        var6.stackSize += var7;
                        var5.onSlotChanged();

                        if (var1.stackSize == 0)
                        {
                            return;
                        }
                    }
                }
            }
        }

        for (var4 = var2; var4 < var3; ++var4)
        {
            var5 = (Slot)this.inventorySlots.get(var4);
            var6 = var5.getStack();

            if (var6 == null)
            {
                var5.putStack(var1);
                var5.onSlotChanged();
                return;
            }
        }
    }

    protected void mergeCrafting(EntityPlayer var1, Slot var2, int var3, int var4)
    {
        int var5 = 0;
        ItemStack var6 = var2.getStack();

        if (var6 != null && var6.stackSize != 0)
        {
            ItemStack var7 = var6.copy();
            int var8 = var7.getMaxStackSize();

            if (var8 == 1)
            {
                var8 = 16;
            }

            do
            {
                if (!this.canFit(var6, var3, var4))
                {
                    return;
                }

                var5 += var6.stackSize;
                this.fitItem(var6, var3, var4);
                var2.onPickupFromSlot(var1, var6);

                if (var5 >= var8)
                {
                    return;
                }

                if (this.slotDraft.isLastUse())
                {
                    return;
                }

                var6 = var2.getStack();

                if (var6 == null || var6.stackSize == 0)
                {
                    return;
                }
            }
            while (CoreLib.compareItemStack(var6, var7) == 0);
        }
    }

    public void handleGuiEvent(Packet212GuiEvent var1)
    {
        if (this.tileAdvBench.worldObj != null && !CoreLib.isClient(this.tileAdvBench.worldObj))
        {
            if (var1.eventId == 1)
            {
                ItemStack var2 = this.tileAdvBench.getStackInSlot(9);

                if (var2 != null && var2.itemID == RedPowerBase.itemPlanBlank.itemID)
                {
                    ItemStack var3 = new ItemStack(RedPowerBase.itemPlanFull);
                    var3.stackTagCompound = new NBTTagCompound();
                    NBTTagCompound var4 = new NBTTagCompound();
                    this.craftResult.getStackInSlot(0).writeToNBT(var4);
                    var3.stackTagCompound.setTag("result", var4);
                    NBTTagList var5 = new NBTTagList();

                    for (int var6 = 0; var6 < 9; ++var6)
                    {
                        ItemStack var7 = this.craftMatrix.getStackInSlot(var6);

                        if (var7 != null)
                        {
                            ItemStack var8 = CoreLib.copyStack(var7, 1);
                            NBTTagCompound var9 = new NBTTagCompound();
                            var8.writeToNBT(var9);
                            var9.setByte("Slot", (byte)var6);
                            var5.appendTag(var9);
                        }
                    }

                    var3.stackTagCompound.setTag("requires", var5);
                    this.tileAdvBench.setInventorySlotContents(9, var3);
                }
            }
        }
    }

	@Override
	public void handleGuiEvent(Packet212GuiEvent var1)
	{
		// TODO 自动生成的方法存根
		
	}
}
//
class ContainerNull extends Container
{
    public boolean canInteractWith(EntityPlayer var1)
    {
        return false;
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(IInventory var1) {}
}
//
class InventorySubUpdate implements IInventory
{
    int size;
    int start;
    IInventory parent;

    final ContainerAdvBench this$0;

    public InventorySubUpdate(ContainerAdvBench var1, IInventory var2, int var3, int var4)
    {
        this.this$0 = var1;
        this.parent = var2;
        this.start = var3;
        this.size = var4;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.size;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int var1)
    {
        return this.parent.getStackInSlot(var1 + this.start);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        ItemStack var3 = this.parent.decrStackSize(var1 + this.start, var2);

        if (var3 != null)
        {
            this.this$0.onCraftMatrixChanged(this);
        }

        return var3;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        return this.parent.getStackInSlotOnClosing(var1 + this.start);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int var1, ItemStack var2)
    {
        this.parent.setInventorySlotContents(var1 + this.start, var2);
        this.this$0.onCraftMatrixChanged(this);
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.parent.getInvName();
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return this.parent.getInventoryStackLimit();
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        this.this$0.onCraftMatrixChanged(this);
        this.parent.onInventoryChanged();
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return false;
    }

    public void openChest() {}

    public void closeChest() {}

	@Override
	public boolean isInvNameLocalized()
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		// TODO 自动生成的方法存根
		return true;
	}
}
