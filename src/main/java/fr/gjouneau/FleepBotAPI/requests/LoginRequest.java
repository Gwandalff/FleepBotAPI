package fr.gjouneau.FleepBotAPI.requests;

class LoginRequest extends BaseRequest {

	private static final long serialVersionUID = 4740776490733178882L;

	public LoginRequest(String email, String password) {
		super("account/login", null);
		put("email", email);
		put("password", password);
		put("remember_me", true);
	}
}
