package fr.gjouneau.FleepBotAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.gjouneau.FleepBotAPI.requests.HttpResp;
import fr.gjouneau.FleepBotAPI.requests.RequestUtils;

public abstract class FleepBot {
	protected final String myAccountId;

	public FleepBot(String email, String password) {

		// login
		HttpResp resp = RequestUtils.logIn(email, password);
		if (resp != null && resp.getResponseCode() == 200) {
			System.out.println("Login successful.");
			String ticket = resp.getString("ticket");
			myAccountId = resp.getString("account_id");
			RequestUtils.init(ticket);
		} else {
			myAccountId = "";
			System.out.println("Login failed.");
			System.exit(-1);
		}

	}
	
	public final void start() {
		// request last state of events
		long eventHorizon = RequestUtils.requestInitialEventHorizon();
		// start the bot
		if (eventHorizon >= 0) {
			startBot(eventHorizon);
		}
	}

	

	/**
	 * Start actual bot It will connect to server using long poll pattern.
	 * Connection will stay alive for 90 seconds. If new events appear, server
	 * returns instantly. If no events appear, server returns empty array and client
	 * will initiate another request.
	 * 
	 * @param eventHorizon
	 */
	private void startBot(long initialEventHorizon) {
		long eventHorizon = initialEventHorizon;
		while (true) {
			HttpResp resp = RequestUtils.pollRequest(eventHorizon);
			if (resp != null && resp.getResponseCode() == 200) {
				eventHorizon = resp.getLong("event_horizon");
				JSONArray list = resp.getList("stream");
				if (list != null) {
					handlePollResults(list);
				}
			} else if (resp != null && resp.getResponseCode() == 401) {
				// current credentials are not valid any more
				System.out.println("Session expired, stopping program");
				System.exit(-1);
				return;
			} else {
				System.out.println("Connection lost.. retrying in 1 minute");
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * Go through the array of events returned by long poll
	 * 
	 * @param stream
	 */
	private void handlePollResults(JSONArray stream) {
		System.out.println("handlePollResults: " + stream.size());
		for (int i = 0; i < stream.size(); i++) {
			JSONObject item = (JSONObject) stream.get(i);
			String type = (String) item.get("mk_rec_type");
			
			// dispatch event handling
			switch (type) {
			case "message":
				handleMessage(item);
				break;
				
			case "conv":
				handleConv(item);
				break;
				
			case "label":
				handleLabel(item);
				break;
				
			case "lock":
				handleLock(item);
				break;
				
			case "contact":
				handleContact(item);
				break;
				
			case "file":
				handleFile(item);
				break;
				
			case "preview":
				handlePreview(item);
				break;
				
			case "activity":
				handleActivity(item);
				break;
				
			case "hook":
				handleHook(item);
				break;
				
			case "request":
				handleRequest(item);
				break;
				
			case "team":
				handleTeam(item);
				break;
				
			case "smtp":
				handleSMTP(item);
				break;
				
			case "upload":
				handleUpload(item);
				break;
				
			case "billing":
				handleBilling(item);
				break;
				
			case "transaction":
				handleTransaction(item);
				break;
				
			case "invoice":
				handleInvoice(item);
				break;

			default:
				handleDefault(item);
				break;
			}
		}
	}
	
	protected void handleDefault		(JSONObject message) {}
	protected void handleMessage		(JSONObject message) {}
	protected void handleConv			(JSONObject message) {}
	protected void handleLabel			(JSONObject message) {}
	protected void handleLock			(JSONObject message) {}
	protected void handleContact		(JSONObject message) {}
	protected void handleFile			(JSONObject message) {}
	protected void handlePreview		(JSONObject message) {}
	protected void handleActivity		(JSONObject message) {}
	protected void handleHook			(JSONObject message) {}
	protected void handleRequest		(JSONObject message) {}
	protected void handleTeam			(JSONObject message) {}
	protected void handleSMTP			(JSONObject message) {}
	protected void handleUpload			(JSONObject message) {}
	protected void handleBilling		(JSONObject message) {}
	protected void handleTransaction	(JSONObject message) {}
	protected void handleInvoice		(JSONObject message) {}

}
