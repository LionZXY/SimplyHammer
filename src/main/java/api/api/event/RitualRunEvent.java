package api.api.event;

import cpw.mods.fml.common.eventhandler.Cancelable;

@Cancelable
public class RitualRunEvent extends RitualEvent
{
	
	
	public RitualRunEvent(api.api.rituals.IMasterRitualStone mrs, String ownerKey, String ritualKey)
	{
		super(mrs, ownerKey, ritualKey);
	}

}
