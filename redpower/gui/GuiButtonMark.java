package redpower.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonMark extends GuiButton
{

	public GuiButtonMark(int par1, int par2, int par3)
	{
		super(par1, par2, par3,null);
	}

	public GuiButtonMark(int id, int x, int y, int w, int h)
	{
		super(id, x, y, w, h, null);
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft,int par2,int par3)
    {
        if (this.drawButton)
        {
            par1Minecraft.getTextureManager().bindTexture(GuiAdvBench.AdvGuiTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 177,1,12,12);
            //this.mouseDragged(par1Minecraft, par2, par3);
        }
    }
}
