package net.sf.jaer2.eventio.events;

public class BaseEvent implements Event {
	private boolean valid = true;

	public int timestamp;

	public int x;
	public int y;

	@Override
	public Class<? extends Event> getEventType() {
		return this.getClass();
	}

	@Override
	public void invalidate() {
		setValid(false);
	}

	@Override
	public void setValid(final boolean validEvent) {
		valid = validEvent;
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	@Override
	public int getTimestamp() {
		return timestamp;
	}
}
