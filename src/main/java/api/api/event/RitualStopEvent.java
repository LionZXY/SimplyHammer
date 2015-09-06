package api.api.event;

public class RitualStopEvent extends RitualEvent
{
	public final api.api.rituals.RitualBreakMethod method;
	public RitualStopEvent(api.api.rituals.IMasterRitualStone mrs, String ownerKey, String ritualKey, api.api.rituals.RitualBreakMethod method)
	{
		super(mrs, ownerKey, ritualKey);
		
		this.method = method;
	}
}
