package redpower.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import redpower.invventory.ContainerAdvBench;
import redpower.item.ItemDraft;
import redpower.item.ItemProxy;
import redpower.net.PacketID;
import redpower.tileentity.TileAdvBench;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiAdvBench extends GuiContainer
{
	public static final ResourceLocation AdvGuiTextures = new ResourceLocation("redpower:textures/gui/advbench.png");
	private TileAdvBench tile;
	private GuiButton buttonmark=new GuiButtonMark(1,(this.width - this.xSize)/2+18,(this.height - this.ySize)/2+55, 14, 14);
	public GuiAdvBench(InventoryPlayer par1InventoryPlayer, TileAdvBench par2tileAdvbench)
	{
		super(new ContainerAdvBench(par1InventoryPlayer, par2tileAdvbench));
		this.tile=par2tileAdvbench;
		this.xSize=174;
		this.ySize=220;
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s =this.tile.getInvName();
        this.fontRenderer.drawString(I18n.getString(s), this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        
        if(this.tile.getStackInSlot(0)!=null&&this.tile.getStackInSlot(0).getItem() instanceof ItemDraft&&this.tile.getStackInSlot(0).getItemDamage()==0&& ((ContainerAdvBench)this.inventorySlots).hasresult )
        {
//        	System.out.println((this.width - this.xSize)/2+18+","+((this.height - this.ySize)/2+55));
//        	this.buttonList.add(buttonmark);
        	buttonmark.drawButton=true;
        }
        else
        {
//        	this.buttonList.remove(buttonmark);
        	buttonmark.drawButton=false;
        }
        //buttonmark.drawButton(this.mc, 0, 0);
    }
	@Override
	public void initGui()
	{
		super.initGui();
		this.buttonList.add(buttonmark);
		buttonmark.xPosition=(this.width - this.xSize)/2+18;
		buttonmark.yPosition=(this.height - this.ySize)/2+55;
		buttonmark.drawButton=this.tile.getStackInSlot(0)!=null&&this.tile.getStackInSlot(0).getItem() instanceof ItemDraft&&this.tile.getStackInSlot(0).getItemDamage()==0;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(AdvGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        //int i1;
        /*
        if (this.tile.isBurning())
        {
            i1 = this.tile.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.tile.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        */
	}
	@Override
	public void actionPerformed(GuiButton button)
	{
		if(button instanceof GuiButtonMark)
		{
			((ContainerAdvBench)this.inventorySlots).saveMatrix();
			this.sendPacket(this.saveRecipe());
		}
	}
	
	private void sendPacket(ItemStack draft)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(16);
	    DataOutputStream outputStream = new DataOutputStream(bos);
	    try {
	    	outputStream.writeByte(PacketID.guiAdvUpdate.ordinal());
	        outputStream.writeInt(this.tile.xCoord);
	        outputStream.writeInt(this.tile.yCoord);
	        outputStream.writeInt(this.tile.zCoord);
	        //write the relevant information here... exemple:
	        //outputStream.writeUTF(theTextToDisplayTextField.getText());
	        //outputStream.writeUTF("UPDATE");
	        Packet.writeItemStack(draft,outputStream);
	    } catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "RedPower";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToServer(packet);
		System.out.println("Sended");
	}
	
	private ItemStack saveRecipe()
	{
		//ItemStack draft=this.tile.getStackInSlot(0);
		ItemStack draft=new ItemStack(ItemProxy.draft,1,1);
		//InventoryCrafting matrix=((ContainerAdvBench)this.inventorySlots);
		/*NBTTagCompound tag=draft.getTagCompound();
		if(tag==null)*/NBTTagCompound tag=new NBTTagCompound();
		NBTTagList taglist = new NBTTagList();
		for (int i = 1; i <10; i++)
        {
            if (this.tile.getStackInSlot(i)!= null)
            {
                NBTTagCompound temptag = new NBTTagCompound();
                //!
                temptag.setByte("Slot", (byte)i);
                ItemStack stack=this.tile.getStackInSlot(i);
                if(stack.getTagCompound()==null)new ItemStack(stack.getItem(),1,stack.getItemDamage()).writeToNBT(temptag);
                else stack.writeToNBT(temptag);
                taglist.appendTag(temptag);
            }
        }
		tag.setTag("Items",taglist);
		tag.setTag("Result",((ContainerAdvBench)this.inventorySlots).getResultNBT());
		tag.setName("Recipe");
		draft.setItemDamage(1);
		draft.setTagCompound(tag);
		return draft;
	}
}
