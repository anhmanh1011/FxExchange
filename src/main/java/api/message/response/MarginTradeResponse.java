package api.message.response;

import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class MarginTradeResponse extends BaseResponse {

    private double margin;

    public MarginTradeResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONObject rd = (JSONObject) this.getReturnData();
        if (rd != null) {
	        this.margin  = (Double) rd.get("margin");
        }
    }

	public double getMargin() {
		return margin;
	}

	@Override
	public String toString() {
		return "MarginTradeResponse {margin=" + margin + "}";
	}
}
