package api.message.response;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.records.TradeRecord;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class TradeRecordsResponse extends BaseResponse {
    
	private List<TradeRecord> tradeRecords = new LinkedList<TradeRecord>();

    @SuppressWarnings("rawtypes")
	public TradeRecordsResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONArray arr = (JSONArray) this.getReturnData();
        for (Iterator it = arr.iterator(); it.hasNext();) {
            JSONObject e = (JSONObject) it.next();
            TradeRecord record = new TradeRecord();
            record.setFieldsFromJSONObject(e);
            tradeRecords.add(record);
        }
    }

    public List<TradeRecord> getTradeRecords() {
        return tradeRecords;
    }

    @Override
    public String toString() {
        return "TradeRecordsResponse{" + "tradeRecords=" + tradeRecords + '}';
    }
}
