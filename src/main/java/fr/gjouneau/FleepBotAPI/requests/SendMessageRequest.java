package fr.gjouneau.FleepBotAPI.requests;

class SendMessageRequest extends BaseRequest {

	private static final long serialVersionUID = -3725738600211892403L;

	public SendMessageRequest(String message, String conversationId, String ticket) {
		super("message/send/" + conversationId, ticket);
		put("message", message);
	}
}
