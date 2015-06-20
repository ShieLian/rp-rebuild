package redpower.item;

public class ItemColoredBucket extends ItemMetaed
{
	public ItemColoredBucket(int id,String[] names)
	{
		super(id, 16, "base/items1",96,"coloredbucket",names);
		this.setCreativeTab(redpower.stuff.RPCreativeTab.itemtab);
	}
}
