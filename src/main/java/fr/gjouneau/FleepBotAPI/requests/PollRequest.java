package fr.gjouneau.FleepBotAPI.requests;

class PollRequest extends BaseRequest {

	private static final long serialVersionUID = 8858324516199428592L;

	public PollRequest(long eventHorizon, String ticket) {
		super("account/poll", ticket);
		put("event_horizon", eventHorizon);
		put("wait", true);
	}
}
