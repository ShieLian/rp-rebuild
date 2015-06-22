package redpower.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import redpower.RedPower;
import redpower.stuff.RPCreativeTab;
import redpower.tileentity.TileAdvBench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockAdvBench extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	public static Icon icontop,iconbottom,iconfront,iconside;
	public BlockAdvBench(int id)
	{
		super(id, Material.wood);
		 this.setCreativeTab(RPCreativeTab.machinetab);
	}
	@Override
	public BlockAdvBench setUnlocalizedName(String par)
	{
		super.setUnlocalizedName(par);
		return this;
	}
	
	@SideOnly(Side.CLIENT)
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
		if(par2==0)
		{
			return par1==1?icontop:(par1==3?iconfront:iconside);
		}
		return par1 == 1 ? icontop : (par1 == 0 ?iconbottom : (par1 != par2 ?iconside :iconfront));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.setTextureName("redpower:AdvBench");
        iconbottom = par1IconRegister.registerIcon(this.getTextureName() + "_bottom");
        icontop = par1IconRegister.registerIcon(this.getTextureName() + "_top");
        iconfront = par1IconRegister.registerIcon(this.getTextureName() + "_front");
        iconside=par1IconRegister.registerIcon(this.getTextureName() + "_side");
    }
    
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	TileEntity tileEntity = par1World.getBlockTileEntity(x, y, z);
        if (tileEntity == null || par5EntityPlayer.isSneaking())
        {
                return false;
        }
        par5EntityPlayer.openGui(RedPower.instance, 0, par1World, x, y, z);
        return true;
    }
    
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
        dropItems(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    private void dropItems(World world, int x, int y, int z)
    {
        Random rand = new Random();
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (!(tileEntity instanceof TileAdvBench))
        {
        	return;
        }
        TileAdvBench inventory = (TileAdvBench) tileEntity;
        for (int i = 0; i < 28; i++)
        {
        	ItemStack item = inventory.getStackInSlot(i);
        	if (item != null && item.stackSize > 0)
        	{
        		float rx = rand.nextFloat() * 0.8F + 0.1F;
        		float ry = rand.nextFloat() * 0.8F + 0.1F;
        		float rz = rand.nextFloat() * 0.8F + 0.1F;
        		EntityItem entityItem = new EntityItem(world,x + rx, y + ry, z + rz,new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
        		if (item.hasTagCompound())
        		{
        			entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
        		}
        		float factor = 0.05F;
        		entityItem.motionX = rand.nextGaussian() * factor;
        		entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
        		entityItem.motionZ = rand.nextGaussian() * factor;
        		world.spawnEntityInWorld(entityItem);
        		item.stackSize = 0;
        	}
        }
    }
    
    /**
     * set metadata
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
        }

        if (l == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
        }

        if (l == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
        }

        if (l == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
        }
    }
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
            {
                b0 = 3;
            }

            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
            {
                b0 = 2;
            }

            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
            {
                b0 = 5;
            }

            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
            {
                b0 = 4;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileAdvBench();
	}
}
