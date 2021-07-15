package api.message.response;

import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class CommissionDefResponse extends BaseResponse {

    private double commission;
    private double rateOfExchange;

    public CommissionDefResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONObject rd = (JSONObject) this.getReturnData();
        if (rd != null) {
	        this.commission  = (Double) rd.get("commission");
	        this.rateOfExchange = (Double) rd.get("rateOfExchange");
        }
    }

    public double getCommission() {
        return commission;
    }

    public double getRateOfExchange() {
        return rateOfExchange;
    }

	@Override
	public String toString() {
		return "CommissionDefResponse [commission=" + commission
				+ ", rateOfExchange=" + rateOfExchange + "]";
	}
}
