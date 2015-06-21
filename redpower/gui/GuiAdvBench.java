package redpower.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

@SideOnly(Side.CLIENT)
public class GuiAdvBench extends GuiContainer
{
	public static final ResourceLocation AdvGuiTextures = new ResourceLocation("redpower:textures/gui/advbench.png");
	private TileAdvBench tile;
	private GuiButton buttonmark=new GuiButtonMark(1,(this.width - this.xSize)/2+17,(this.height - this.ySize)/2+54, 14, 14);
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
        	buttonmark.drawButton=true;
        }
        else
        {
        	buttonmark.drawButton=false;
        }
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
	//TODO Simplify
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(AdvGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        if(this.tile.getStackInSlot(0)!=null&&this.tile.getStackInSlot(0).getItemDamage()==1)
        {
        	ItemStack[] shadowstacks=ContainerAdvBench.getShadowItems(this.tile.getStackInSlot(0));
        	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            Slot slot;
            int var13;
            int var14;
            for (int index = 0; index < 9; ++index)
            {
                if (shadowstacks[index] != null)
                {
                    slot = this.inventorySlots.getSlot(index);

                    if (slot.getStack() == null)
                    {
                        var13 = slot.xDisplayPosition + k;
                        var14 = slot.yDisplayPosition + l;
                        itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, shadowstacks[index], var13, var14);
                        itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, shadowstacks[index], var13, var14);
                    }
                }
            }
            
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            this.mc.renderEngine.bindTexture(AdvGuiTextures);
            
            ItemStack draft=this.tile.getStackInSlot(0);
            NBTTagList itemtaglist=draft.stackTagCompound.getTagList("Items");
            NBTTagCompound[] itemtags=new NBTTagCompound[itemtaglist.tagCount()];
            ArrayList<Byte> ids=new ArrayList<Byte>(itemtaglist.tagCount());
            HashMap<String,Integer> counter=new HashMap<String,Integer>(itemtaglist.tagCount());
            
            NBTTagCompound temptag=null;ItemStack tempstack=null;
			for(int index=0;index<itemtags.length;index++)
			{
				temptag=(NBTTagCompound) itemtaglist.tagAt(index);
				itemtags[index]=temptag;
				tempstack=ItemStack.loadItemStackFromNBT(temptag);
				String mark = OreDictionary.getOreID(tempstack)==-1?tempstack.getDisplayName():String.valueOf(OreDictionary.getOreID(tempstack));
				/*if(!counter.containsKey(mark))
				{
					counter.put(mark, 1);
				}
				else
				{
					counter.put(mark,counter.get(mark)+1);
				}*/
				counter.put(mark,1);
			}
			
            for(int index=0;index<itemtags.length;index++)
            {
            	itemtags[index]=(NBTTagCompound)itemtaglist.tagAt(index);
            	ids.add(itemtags[index].getByte("Slot"));
            }
            
            for (int index = 0; index < 9; ++index)
            {
                if (shadowstacks[index] != null)
                {
                    slot = this.inventorySlots.getSlot(index);

                    if (slot.getStack() == null)
                    {
                        var13 = slot.xDisplayPosition;
                        var14 = slot.yDisplayPosition;

//                      if ((((ContainerAdvBench)this.inventorySlots)  .satisfyMask & 1 << index) > 0)
                        
                        //if(!ids.contains((byte)index)||((ContainerAdvBench)this.inventorySlots).getMatrixStack(index)!=null&&ItemStack.loadItemStackFromNBT(itemtags[index]).isItemEqual(((ContainerAdvBench)this.inventorySlots).getMatrixStack(index)))
                        if((!ids.contains(new Byte((byte)(index+1))))||((ContainerAdvBench)this.inventorySlots).satisfy(index,shadowstacks[index],counter)!=-1 )
                        {
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                        }
                        else
                        {
                            GL11.glColor4f(1.0F, 0.1F, 0.1F, 0.6F);
                        }

                        this.drawTexturedModalRect(k + var13, l + var14, var13, var14, 16, 16);
                    }
                }
            }

            GL11.glDisable(GL11.GL_BLEND);
        }
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
	        Packet.writeItemStack(draft,outputStream);
	    } catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "RedPower";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToServer(packet);
//		System.out.println("Sended");
	}
	
	private ItemStack saveRecipe()
	{
		ItemStack draft=new ItemStack(ItemProxy.draft,1,1);
		NBTTagCompound tag=new NBTTagCompound();
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
                //-
//                System.out.println(i+":"+this.tile.getStackInSlot(i).getDisplayName());
            }
        }
		tag.setTag("Items",taglist);
		tag.setTag("Result",((ContainerAdvBench)this.inventorySlots).getResultNBT());
		tag.setName("Recipe");
		
		NBTTagCompound display=new NBTTagCompound("display");
		display.setString("Name",getResultDisplayName(((ContainerAdvBench)this.inventorySlots).getResultNBT())+" 蓝图");
		
		tag.setTag("display", display);
		
		draft.setItemDamage(1);
		draft.setTagCompound(tag);
		return draft;
	}
	public static String getResultDisplayName(NBTTagCompound resultNBT)
	{
		int i=ItemStack.loadItemStackFromNBT(resultNBT).stackSize;
		return (i==1?"":(i+"×"))+ItemStack.loadItemStackFromNBT(resultNBT).getDisplayName();
	}
}
