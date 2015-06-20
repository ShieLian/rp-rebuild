package redpower;
import redpower.stuff.RPCreativeTab;
import redpower.tileentity.TileentityProxy;
import redpower.block.BlockProxy;
import redpower.gui.GuiHandler;
import redpower.item.ItemProxy;
import redpower.proxy.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@Mod(modid="redpower",name="redpower",version="0.0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class RedPower
{
	@Instance("redpower")
	public static RedPower instance;
	@SidedProxy(clientSide="redpower.proxy.ClientProxy",serverSide="redpower.proxy.Proxy")
	public static Proxy proxy;
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registry();
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
	}
}
