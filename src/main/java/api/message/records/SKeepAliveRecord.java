package api.message.records;

import org.json.simple.JSONObject;
import api.message.records.BaseResponseRecord;

public class SKeepAliveRecord implements BaseResponseRecord {

	private long timestamp;

	@Override
    public void setFieldsFromJSONObject(JSONObject ob) {
		timestamp = (Long) ob.get("timestamp");
    }

    public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "SKeepAliveRecord [timestamp=" + timestamp + "]";
	}
}
