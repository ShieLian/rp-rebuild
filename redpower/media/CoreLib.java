package redpower.media;

import java.util.Comparator;
import java.util.TreeMap;

import net.minecraft.item.ItemStack;

public class CoreLib
{
	public static Comparator itemStackComparator = new CoreLib$1();
	private static TreeMap oreMap = new TreeMap(itemStackComparator);
	public static String getOreClass(ItemStack var0)
    {
        String var1 = (String)oreMap.get(var0);

        if (var1 != null)
        {
            return var1;
        }
        else
        {
            var0 = new ItemStack(var0.itemID, 1, -1);
            return (String)oreMap.get(var0);
        }
    }
	
    public static boolean matchItemStackOre(ItemStack var0, ItemStack var1)
    {
        String var2 = getOreClass(var0);
        String var3 = getOreClass(var1);
        return var2 != null && var3 != null && var2.equals(var3) ? true : compareItemStack(var0, var1) == 0;
    }
	
    public static int compareItemStack(ItemStack var0, ItemStack var1)
    {
        return var0.itemID != var1.itemID ? var0.itemID - var1.itemID : (var0.getItemDamage() == var1.getItemDamage() ? 0 : (var0.getItem().getHasSubtypes() ? var0.getItemDamage() - var1.getItemDamage() : 0));
    }
    
}
final class CoreLib$1 implements Comparator
{
    public int compare(ItemStack var1, ItemStack var2)
    {
        return CoreLib.compareItemStack(var1, var2);
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((ItemStack)var1, (ItemStack)var2);
    }
}
