package redpower.block;

import static net.minecraftforge.common.EnumPlantType.Cave;
import static net.minecraftforge.common.EnumPlantType.Crop;
import static net.minecraftforge.common.EnumPlantType.Desert;
import static net.minecraftforge.common.EnumPlantType.Nether;
import static net.minecraftforge.common.EnumPlantType.Plains;
import static net.minecraftforge.common.EnumPlantType.Water;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class BlockIndigoFlower extends BlockFlower
{

	public BlockIndigoFlower(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}

	public BlockIndigoFlower(int par1)
	{
		super(par1);
		this.setTickRandomly(false);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		float f=0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
	}

	@Override
    public EnumPlantType getPlantType(World world, int x, int y, int z)
    {
        return Plains;
    }
}
