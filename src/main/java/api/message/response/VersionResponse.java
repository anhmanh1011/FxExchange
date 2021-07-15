package api.message.response;

import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class VersionResponse extends BaseResponse {

    private String version;

    public VersionResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONObject ob = (JSONObject) this.getReturnData();
        this.version = (String) ob.get("version");
    }

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "VersionResponse [version=" + version + "]";
	}
}
