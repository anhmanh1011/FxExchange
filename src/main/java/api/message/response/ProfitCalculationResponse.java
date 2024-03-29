package api.message.response;

import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class ProfitCalculationResponse extends BaseResponse {

    private Double profit;

    public ProfitCalculationResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONObject ob = (JSONObject) this.getReturnData();
        if (ob != null) {
        	this.profit = (Double) ob.get("profit");
        }
    }

    public Double getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "ProfitCalculationResponse{" + "profit=" + profit + '}';
    }
}
