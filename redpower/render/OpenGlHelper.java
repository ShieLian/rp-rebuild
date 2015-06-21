package redpower.render;

import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL13;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class OpenGlHelper
{
	 /**
     * True if the renderer supports multitextures and the OpenGL version != 1.3
     */
    private static boolean useMultitextureARB = false;
	
	/**
     * Sets the current coordinates of the given lightmap texture
     */
    public static void setLightmapTextureCoords(int par0, float par1, float par2)
    {
        if (useMultitextureARB)
        {
            ARBMultitexture.glMultiTexCoord2fARB(par0, par1, par2);
        }
        else
        {
            GL13.glMultiTexCoord2f(par0, par1, par2);
        }
    }
	
}
