package api.message.response;

import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.records.RedirectRecord;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class LoginResponse extends BaseResponse {

	private RedirectRecord redirect;

    public LoginResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONObject redirectJson = super.getRedirectJson();
        if (redirectJson != null) {
        	redirect = new RedirectRecord();
        	redirect.setFieldsFromJSONObject(redirectJson);
        }
    }

    public RedirectRecord getRedirect() {
		return redirect;
	}

	public boolean isRedirectSet() {
    	return this.redirect != null;
    }

	@Override
	public String toString() {
		return "LoginResponse [getStatus()=" + getStatus()
				+ ", getStreamSessionId()=" + getStreamSessionId() + "]";
	}
}
