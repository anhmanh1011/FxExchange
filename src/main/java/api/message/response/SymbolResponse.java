package api.message.response;

import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.records.SymbolRecord;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class SymbolResponse extends BaseResponse {

    private SymbolRecord symbol;
    
    public SymbolResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONObject ob = (JSONObject) this.getReturnData();
        symbol = new SymbolRecord();
        symbol.setFieldsFromJSONObject(ob);
    }

    public SymbolRecord getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "SymbolResponse{" + "symbol=" + symbol + '}';
    }
}
