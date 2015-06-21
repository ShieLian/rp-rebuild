package redpower.stuff.recipes;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import redpower.block.BlockProxy;
import redpower.item.ItemProxy;
import redpower.stuff.IRegister;

public class CraftRecipes implements IRegister {

	@Override
	public void registry()
	{
		GameRegistry.addShapedRecipe(
				new ItemStack(BlockProxy.advbench,1),
				"SSS","B#B","BCB",
				'S',Block.stone,
				'B',Block.planks,
				'#',Block.workbench,
				'C',Block.chest);
		GameRegistry.addShapelessRecipe(
				new ItemStack(ItemProxy.indigoDye,2),
				BlockProxy.indigoFlower);
		OreDictionary.registerOre("dyeBlue", ItemProxy.indigoDye);
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemProxy.draft,"D","P","D",'D',"dyeBlue",'P',Item.paper));
	}

}
