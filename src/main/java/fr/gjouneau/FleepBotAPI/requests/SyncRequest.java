package fr.gjouneau.FleepBotAPI.requests;

class SyncRequest extends BaseRequest {

	private static final long serialVersionUID = 8598017871359618334L;

	public SyncRequest(String ticket) {
		super("account/sync", ticket);
	}
}
