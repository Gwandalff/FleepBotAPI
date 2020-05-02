package fr.gjouneau.FleepBotAPI.requests;

import org.json.simple.JSONObject;

public class BaseRequest extends JSONObject {

	private static final long serialVersionUID = 2718636252081529054L;

	private static final String API_URL = "https://fleep.io/api/";

	private String requestUrl;

	public BaseRequest(String requestUrl, String ticket) {
		this.requestUrl = API_URL + requestUrl;
		if (ticket != null) {
			put("ticket", ticket);
		}
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public String toLog() {
		if (this.containsKey("password")) {
			// do not log actual password
			JSONObject json = new JSONObject(this);
			json.put("password", "********");
			return json.toString();
		}
		return toString();
	}
}
