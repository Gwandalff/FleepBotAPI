package fr.gjouneau.FleepBotAPI.requests;

import java.util.List;

import org.json.simple.JSONArray;

class NewConvRequest extends BaseRequest {

	private static final long serialVersionUID = -3881837583424155401L;

	public NewConvRequest(String ticket, List<String> accountIds, String convName) {
		super("conversation/create", ticket);
		JSONArray ids = new JSONArray();
		ids.addAll(accountIds);
		put("account_ids", ids);
		put("topic", convName);
	}

}
