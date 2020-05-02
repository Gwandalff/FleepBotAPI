package fr.gjouneau.FleepBotAPI.requests;

class DeleteConvRequest extends BaseRequest {

	private static final long serialVersionUID = 897249001755844790L;

	public DeleteConvRequest(String ticket, String convId) {
		super("conversation/delete/"+convId, ticket);
	}

}
