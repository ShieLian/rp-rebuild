package redpower.proxy;

import redpower.stuff.RPCreativeTab;

public class ClientProxy extends Proxy
{
	public void registry()
	{
		RPCreativeTab.registry();
		super.registry();
	}
}
