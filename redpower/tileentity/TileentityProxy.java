package redpower.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileentityProxy
{
	public static void registry()
	{
		GameRegistry.registerTileEntity(TileAdvBench.class,"advbench");
		
	}
}
