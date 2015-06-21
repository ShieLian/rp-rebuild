package redpower.item;

import redpower.stuff.RPCreativeTab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;

public class ItemProxy
{
	final static String[] colorlist=new String[]{"white","orange","magenta","lightblue","yellow","lime","pink","gray","silver","cyan","purple","blue","brown","green","red","black"};
	public static Item[] coloredBrushs=new Item[16];
	public static ItemMetaed coloredBucket;
	public static ItemMetaed draft; 
	public static Item indigoDye;
	public static void registry()
	{
		coloredBrushs=ItemColoredBrush.rigistry(new int[]{1000,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015});
		coloredBucket= new ItemColoredBucket(1016,colorlist);
		draft=new ItemDraft(1017);
		indigoDye=new Item(1018).setCreativeTab(RPCreativeTab.itemtab).setUnlocalizedName("indigoDye").setTextureName("repower:base/items1/80.png");
	}
}
