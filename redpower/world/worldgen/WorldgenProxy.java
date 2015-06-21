package redpower.world.worldgen;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import redpower.block.BlockProxy;
import redpower.stuff.IRegister;

public class WorldgenProxy implements IRegister
{

	@Override
	public void registry()
	{
		//ForgeHooks.grassList.add(new GrassEntry(BlockProxy.indigoFlower,0, 20));
		MinecraftForge.addGrassPlant(BlockProxy.indigoFlower,0,20);
	}
}
