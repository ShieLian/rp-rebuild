package redpower.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import redpower.stuff.RPCreativeTab;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockAlloyFurnace extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	public Icon icontop,iconbottom,iconfront_off,iconfront_on,iconside;
	private boolean isActive; 
	public BlockAlloyFurnace(int id)
	{
		super(id,Material.rock);
		this.setCreativeTab(RPCreativeTab.machinetab);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int par1, int par2)
    {
		if(par2==0)
		{
			return par1==1?icontop:(par1==3?iconfront_off:iconside);
		}
        return par1 == 1 ? this.icontop : (par1 == 0 ? this.iconbottom : (par1 != par2 ? this.blockIcon : this.isActive?this.iconfront_on:this.iconfront_off));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
    	this.setTextureName("redpower:AlloyFurnace");
        this.iconside = par1IconRegister.registerIcon(this.getTextureName()+"_side");
        this.iconfront_on = par1IconRegister.registerIcon( this.getTextureName()+"_front_on");
        this.iconfront_off = par1IconRegister.registerIcon( this.getTextureName()+"_front_off");
        this.icontop = par1IconRegister.registerIcon(this.getTextureName()+"_top");
        this.iconbottom=par1IconRegister.registerIcon(this.getTextureName()+"_top");
    }

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;
	}
}
