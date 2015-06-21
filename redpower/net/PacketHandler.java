package redpower.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import redpower.item.ItemDraft;
import redpower.tileentity.TileAdvBench;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{

	public PacketHandler()
	{
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player)
	{
		if (packet.channel.equals("RedPower"))
		{
			DataInputStream inputStream = new DataInputStream(
					new ByteArrayInputStream(packet.data));
			int id = -1;
			try
			{
				id = inputStream.readByte();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			switch (id)
			{
			case 0:
				/*int x=0,y=0,z=0;
				try{x=inputStream.}catch(Exception e){e.printStackTrace();}*/
				handleAdvGuiUpdate(inputStream, player);
				break;
			default:
				break;
			}
		}
	}

	private void handleAdvGuiUpdate(DataInputStream inputStream,Player player)
	{
		// TODO 自动生成的方法存根
//		System.out.println("handling");
		int x=0,y=0,z=0;
		try
		{
			x=inputStream.readInt();y=inputStream.readInt();z=inputStream.readInt();
//			System.out.println(x+","+y+","+z);
		} catch (IOException e){e.printStackTrace();}
		System.out.println(player instanceof EntityPlayerMP);
		if(player instanceof EntityPlayerMP)
		{
//			System.out.println("Configered");
			TileEntity tile=((EntityPlayerMP)player).worldObj.getBlockTileEntity(x,y,z);
			ItemStack draft = null;
			try
			{
				draft = Packet.readItemStack(inputStream);
			} catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(tile instanceof TileAdvBench&&draft.getItem() instanceof ItemDraft)
			{
				tile=(TileAdvBench)tile;
				((TileAdvBench) tile).setInventorySlotContents(0,draft);
			}
		}
	}

}
