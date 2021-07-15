package api.message.response;

import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class LogoutResponse extends BaseResponse {

    public LogoutResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
    }

    @Override
    public String toString() {
        return "LogoutResponse{" + '}';
    }
}
