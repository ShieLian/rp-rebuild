package redpower.lib;

import net.minecraft.nbt.NBTTagCompound;

public class CompareLib
{

	public static boolean compareNBT(NBTTagCompound tag1,
			NBTTagCompound tag2)
	{
		if((tag2!=null&&tag1==null)||(tag1!=null&&tag2==null)) return false;
		if(tag1==tag2) return true;
		if(tag1.equals(tag2)) return true;
		return false;
	}

}
