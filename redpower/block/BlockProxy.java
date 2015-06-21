package redpower.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockProxy
{
	public static BlockAdvBench advbench;
	public static BlockAlloyFurnace alloyfurnaceoff;
	public static BlockAlloyFurnace alloyfurnaceon;
	public static BlockFlower indigoFlower;
	public static void registry()
	{
		advbench=(new BlockAdvBench(500)).setUnlocalizedName("advbench");
		
		GameRegistry.registerBlock(advbench);
		alloyfurnaceoff=(BlockAlloyFurnace) new BlockAlloyFurnace(501).setUnlocalizedName("alloyfurnaceoff");
		GameRegistry.registerBlock(alloyfurnaceoff);
		//alloyfurnaceon=(BlockAlloyFurnace) new BlockAlloyFurnace(502,true).setUnlocalizedName("alloyfurnaceon");
		//GameRegistry.registerBlock(alloyfurnaceon);
		
		indigoFlower=(BlockFlower)(new BlockIndigoFlower(502)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("indigoFlower").setTextureName("redpower:worlditems11");
		GameRegistry.registerBlock(indigoFlower);
	}
}
