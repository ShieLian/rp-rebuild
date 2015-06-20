package redpower.gui;

import redpower.invventory.ContainerAdvBench;
import redpower.tileentity.TileAdvBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	public GuiHandler() 
	{
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof TileAdvBench)
        {
        	return new ContainerAdvBench(player.inventory, (TileAdvBench) tileEntity);
        }
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if(tileEntity instanceof TileAdvBench)
        {
        	return new GuiAdvBench(player.inventory, (TileAdvBench) tileEntity);
        }
        return null;
	}

}
