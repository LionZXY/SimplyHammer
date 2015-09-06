package api.api.event;

import cpw.mods.fml.common.eventhandler.Event;

public class RitualEvent extends Event
{
	public final api.api.rituals.IMasterRitualStone mrs;
	public String ownerKey;
	public final String ritualKey;
	
	public RitualEvent(api.api.rituals.IMasterRitualStone mrs, String ownerKey, String ritualKey)
	{
		this.mrs = mrs;
		this.ownerKey = ownerKey;
		this.ritualKey = ritualKey;
	}
}
