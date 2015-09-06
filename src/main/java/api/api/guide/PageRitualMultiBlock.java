package api.api.guide;

import java.util.List;

import net.minecraft.item.ItemStack;

public class PageRitualMultiBlock extends PageMultiBlock 
{
	private static ItemStack blankStone;
	private static ItemStack waterStone;
	private static ItemStack fireStone;
	private static ItemStack earthStone;
	private static ItemStack airStone;
	private static ItemStack duskStone;
	private static ItemStack dawnStone;
	static
	{
//		blankStone = new ItemStack(ModBlocks.ritualStone, 1, RitualComponent.BLANK);
//		waterStone = new ItemStack(ModBlocks.ritualStone, 1, RitualComponent.WATER);
//		fireStone = new ItemStack(ModBlocks.ritualStone, 1, RitualComponent.FIRE);
//		earthStone = new ItemStack(ModBlocks.ritualStone, 1, RitualComponent.EARTH);
//		airStone = new ItemStack(ModBlocks.ritualStone, 1, RitualComponent.AIR);
//		duskStone = new ItemStack(ModBlocks.ritualStone, 1, RitualComponent.DUSK);
//		dawnStone = new ItemStack(ModBlocks.ritualStone, 1, RitualComponent.DAWN);
	}
	
	private PageRitualMultiBlock(ItemStack[][][] structure)
	{
    	super(structure);
	}
	
	public static PageRitualMultiBlock getPageForRitual(String ritualID)
	{
		return getPageForRitual(api.api.rituals.Rituals.getRitualList(ritualID));
	}
	
	public static PageRitualMultiBlock getPageForRitual(List<api.api.rituals.RitualComponent> ritualComponents)
	{
		int minX = 0;
		int minY = 0;
		int minZ = 0;
		
		int maxX = 0;
		int maxY = 0;
		int maxZ = 0;
		
		for(api.api.rituals.RitualComponent comp : ritualComponents)
		{
			minX = Math.min(comp.getX(), minX);
			minY = Math.min(comp.getY(), minY);
			minZ = Math.min(comp.getZ(), minZ);

			maxX = Math.max(comp.getX(), maxX);
			maxY = Math.max(comp.getY(), maxY);
			maxZ = Math.max(comp.getZ(), maxZ);
		}
		
		System.out.println("Min: (" + minX + ", " + minY + ", " + minZ + "), Max: (" + maxX + ", " + maxY + ", " + maxZ + ")");
		
		ItemStack[][][] tempStructure = new ItemStack[maxY-minY+1][maxX-minX+1][maxZ-minZ+1]; //First value is vertical, second is down to the left, third is down to the right
		
		for(api.api.rituals.RitualComponent comp : ritualComponents)
		{
			tempStructure[comp.getY() - minY][comp.getX() - minX][comp.getZ() - minZ] = getStackForRitualStone(comp.getStoneType());
		}
		
//		tempStructure[-minY][-minX][-minZ] = new ItemStack(ModBlocks.blockMasterStone);

		return new PageRitualMultiBlock(tempStructure);
	}
	
	private static ItemStack getStackForRitualStone(int type)
	{
		switch(type)
		{
		case api.api.rituals.RitualComponent.BLANK:
			return blankStone;
		case api.api.rituals.RitualComponent.WATER:
			return waterStone;
		case api.api.rituals.RitualComponent.FIRE:
			return fireStone;
		case api.api.rituals.RitualComponent.EARTH:
			return earthStone;
		case api.api.rituals.RitualComponent.AIR:
			return airStone;
		case api.api.rituals.RitualComponent.DUSK:
			return duskStone;
		case api.api.rituals.RitualComponent.DAWN:
			return dawnStone;
		}
		return blankStone;
	}
}
