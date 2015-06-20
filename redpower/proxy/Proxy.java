package redpower.proxy;

import redpower.item.ItemProxy;
import redpower.tileentity.TileentityProxy;
import redpower.block.BlockProxy;

public class Proxy {

	public void registry()
	{
		ItemProxy.registry();
		BlockProxy.registry();
		TileentityProxy.registry();
	}

}
