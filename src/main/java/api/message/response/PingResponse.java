package api.message.response;

import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class PingResponse extends BaseResponse {

    public PingResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
    }

	@Override
	public String toString() {
		return "PingResponse ["+ super.getStatus() + "]";
	}
}
