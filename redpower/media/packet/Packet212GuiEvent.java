package redpower.media.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;

import redpower.gui.IHandleGuiEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet212GuiEvent extends PacketVLC
{
    public int eventId;
    public int windowId;
    private int cnt1 = 0;
    private int size = 0;

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream var1) throws IOException
    {
        this.windowId = var1.read();

        if (this.windowId == -1)
        {
            throw new IOException("Not enough data");
        }
        else
        {
            this.eventId = (int)this.readUVLC(var1);
            int var2 = (int)this.readUVLC(var1);

            if (var2 > 65535)
            {
                throw new IOException("Packet too big");
            }
            else
            {
                this.size = this.cnt1 + var2 + 1;
                byte[] var3 = new byte[var2];
                int var4;

                for (int var5 = 0; var2 - var5 > 0; var5 += var4)
                {
                    var4 = var1.read(var3, var5, var2 - var5);

                    if (var4 < 1)
                    {
                        throw new IOException("Not enough data");
                    }

                    if (var5 + var4 >= var2)
                    {
                        break;
                    }
                }

                this.bodyin = new ByteArrayInputStream(var3);
            }
        }
    }

    public void encode()
    {
        this.headout.write(this.windowId);
        writeUVLC(this.headout, (long)this.eventId);
        writeUVLC(this.headout, (long)this.bodyout.size());
        this.size = this.headout.size() + this.bodyout.size();
        this.fixLocalPacket();
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler var1)
    {
        this.processPacket212(this, var1);
    }
    public void processPacket212(Packet212GuiEvent var1, NetHandler var2)
    {
        if (var2 instanceof NetServerHandler)
        {
            NetServerHandler var3 = (NetServerHandler)var2;
            EntityPlayerMP var4 = var3.getPlayer();

            if (var4.openContainer != null && var4.openContainer.windowId == var1.windowId)
            {
                if (var4.openContainer instanceof IHandleGuiEvent)
                {
                    IHandleGuiEvent var5 = (IHandleGuiEvent)var4.openContainer;
                    var5.handleGuiEvent(var1);
                }
            }
        }
    }

	@Override
	public void readPacketData(DataInput datainput) throws IOException
	{
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void writePacketData(DataOutput dataoutput) throws IOException
	{
		// TODO 自动生成的方法存根
		
	}
}
