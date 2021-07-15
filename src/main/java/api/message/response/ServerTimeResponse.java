package api.message.response;

import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class ServerTimeResponse extends BaseResponse{

    private long time;
    public ServerTimeResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONObject ob = (JSONObject) this.getReturnData();
        this.time = (Long) ob.get("time");
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "ServerTimeResponse{" + "time=" + time + '}';
    }
}
