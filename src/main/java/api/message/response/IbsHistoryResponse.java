package api.message.response;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.records.IbRecord;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class IbsHistoryResponse extends BaseResponse {

    private List<IbRecord> ibRecords = new LinkedList<IbRecord>();

    @SuppressWarnings("rawtypes")
    public IbsHistoryResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONArray ibRecords = (JSONArray) this.getReturnData();
        for (Iterator it = ibRecords.iterator(); it.hasNext();) {
            JSONObject e = (JSONObject) it.next();
            IbRecord ibRecord = new IbRecord();
            ibRecord.setFieldsFromJSONObject(e);
            this.ibRecords.add(ibRecord);
        }
    }

    public List<IbRecord> getIbRecords() {
        return ibRecords;
    }

    @Override
    public String toString() {
        return "IbsHistoryResponse{" + "ibRecords=" + ibRecords + '}';
    }
}
