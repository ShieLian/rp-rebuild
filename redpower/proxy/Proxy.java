package redpower.proxy;

import redpower.item.ItemProxy;
import redpower.stuff.recipes.CraftRecipes;
import redpower.stuff.recipes.SmeltRecipes;
import redpower.tileentity.TileentityProxy;
import redpower.block.BlockProxy;

public class Proxy {

	public void registry()
	{
		ItemProxy.registry();
		BlockProxy.registry();
		TileentityProxy.registry();
		new CraftRecipes().registry();
		new SmeltRecipes().registry();
	}

}
