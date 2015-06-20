package redpower.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;

public class ItemProxy
{
	final static String[] colorlist=new String[]{"white","orange","magenta","lightblue","yellow","lime","pink","gray","silver","cyan","purple","blue","brown","green","red","black"};
	public static Item[] coloredbrushs=new Item[16];
	public static ItemMetaed coloredbucket;
	public static ItemMetaed draft; 
	public static void registry()
	{
		coloredbrushs=ItemColoredBrush.rigistry(new int[]{1000,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015});
		coloredbucket= new ItemColoredBucket(1016,colorlist);
		draft=new ItemDraft(1017);
	}
}
