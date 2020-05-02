package fr.gjouneau.FleepBotAPI.requests;

import java.io.IOException;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

public class RequestUtils {
	
	private static HttpHelper httpHelper = new HttpHelper();
	private static String ticket;
	
	public static void init(String ticket) {
		RequestUtils.ticket = ticket;
	}
	
	/**
	 * Log the bot
	 * @param email
	 * @param password
	 * @return HTTP response
	 */
	public static HttpResp logIn(String email, String password) {
		System.out.println("Logging in with " + email + "...");
		LoginRequest req = new LoginRequest(email, password);
		return httpHelper.doRequest(req);
	}
	
	/**
	 * Request for current event horizon. We don't want this bot to receive any
	 * history from the server, but only new messages Therefore we need to request
	 * for up to date event horizon to start the long-poll with
	 * 
	 * @return current event horizon
	 */
	public static long requestInitialEventHorizon() {
		SyncRequest req = new SyncRequest(ticket);
		HttpResp resp = httpHelper.doRequest(req);
		if (resp != null && resp.getResponseCode() == 200) {
			long eventHorizon = resp.getLong("event_horizon");
			System.out.println("Initial event horizon: " + eventHorizon);
			return eventHorizon;
		}
		return -1;
	}
	
	public static HttpResp pollRequest(long eventHorizon) {
		PollRequest req = new PollRequest(eventHorizon, ticket);
		return httpHelper.doRequest(req);
	}
	
	/**
	 * messages are received in XML format.
	 * Plain text should be sent back.
	 * @return
	 */
	public static String parseXmlToText(String message) {
		System.out.println("parseXmlToText(): " + message);
		try {
			return XmlParser.parse(message);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Send message to specific conversation
	 * @param conversationId
	 * @param message
	 */
	public static void sendMessage(String conversationId, String message) {
		System.out.println("sendMessage(): " + message);
		SendMessageRequest req = new SendMessageRequest(message, conversationId, ticket);
		httpHelper.doRequest(req);
	}
	
	/**
	 * create a new conversation with a group of users
	 * @param userIds
	 * @param convName
	 * @return the response of the request
	 */
	public static HttpResp createConv(List<String> userIds, String convName) {
		System.out.println("createConv(): " + convName);
		NewConvRequest req = new NewConvRequest(ticket, userIds, convName);
		return httpHelper.doRequest(req);
	}
	
	/**
	 * create a new conversation with one user
	 * @param userId
	 * @param convName
	 * @return the response of the request
	 */
	public static HttpResp createConv(String userId, String convName) {
		System.out.println("createConv(): " + convName);
		NewPrivateConvRequest req = new NewPrivateConvRequest(ticket, userId, convName);
		return httpHelper.doRequest(req);
	}
	
	/**
	 * Make the bot leave the conversation
	 * @param convID
	 */
	public static void leaveConv(String convid) {
		System.out.println("deleteConv(): " + convid);
		DeleteConvRequest req = new DeleteConvRequest(ticket, convid);
		httpHelper.doRequest(req);
	}
}
