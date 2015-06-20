package redpower.media.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet211TileDesc extends PacketVLC
{
    public int subId;
    public int xCoord;
    public int yCoord;
    public int zCoord;
    private int cnt1 = 0;
    private int size = 0;

    public Packet211TileDesc()
    {
        this.isChunkDataPacket = true;
    }

    public Packet211TileDesc(byte[] var1)
    {
        this.bodyin = new ByteArrayInputStream(var1);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream var1) throws IOException
    {
        this.subId = var1.read();

        if (this.subId == -1)
        {
            throw new IOException("Not enough data");
        }
        else
        {
            this.xCoord = (int)this.readVLC(var1);
            this.yCoord = (int)this.readVLC(var1);
            this.zCoord = (int)this.readVLC(var1);
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
        this.headout.write(this.subId);
        writeVLC(this.headout, (long)this.xCoord);
        writeVLC(this.headout, (long)this.yCoord);
        writeVLC(this.headout, (long)this.zCoord);
        writeUVLC(this.headout, (long)this.bodyout.size());
        this.size = this.headout.size() + this.bodyout.size();
        this.fixLocalPacket();
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler var1)
    {
        if (var1 instanceof NetServerHandler)
        {
            NetServerHandler var3 = (NetServerHandler)var1;
            EntityPlayerMP var4 = var3.getPlayer();
            World var5 = var4.worldObj;

            if (var5.blockExists(this.xCoord, this.yCoord, this.zCoord))
            {
                TileEntity var6 = var5.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);

                if (var6 instanceof IHandlePackets)
                {
                    ((IHandlePackets)var6).handlePacket(this);
                    return;
                }
            }
        }
    }

    public void processPacket211(Packet211TileDesc time, NetHandler var1)
    {
        if (var1 instanceof NetServerHandler)
        {
            NetServerHandler var3 = (NetServerHandler)var1;
            EntityPlayerMP var4 = var3.getPlayer();
            World var5 = var4.worldObj;

            if (var5.blockExists(time.xCoord, time.yCoord, time.zCoord))
            {
                TileEntity var6 = var5.getBlockTileEntity(time.xCoord, time.yCoord, time.zCoord);

                if (var6 instanceof IHandlePackets)
                {
                    ((IHandlePackets)var6).handlePacket(time);
                    return;
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
