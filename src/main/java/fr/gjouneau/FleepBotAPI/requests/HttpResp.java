package fr.gjouneau.FleepBotAPI.requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HttpResp {
	
	private int responseCode;
	private JSONObject response;
	
	public HttpResp(int responseCode, String responseText) throws ParseException {
		this.responseCode = responseCode;
		JSONParser parser = new JSONParser();
		this.response = (JSONObject) parser.parse(responseText);
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getString(String name) {
		return (String) response.get(name);
	}
	
	public long getLong(String name) {
		boolean exist = response.containsKey(name);
		if (exist) {
			return (Long) response.getOrDefault(name, 0L);
		} else {
			return -1;
		}
	}
	
	public JSONArray getList(String name) {
		return (JSONArray) response.get(name);
	}
	
	public JSONObject getJSONObject(String name) {
		return (JSONObject) response.get(name);
	}
	
	@Override
	public String toString() {
		return "httpResponse[responseCode: " + responseCode + ", responseText: " + response.toString() + "]";
	}
}
