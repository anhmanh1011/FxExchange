package api.message.records;

import org.json.simple.JSONObject;
import api.message.records.BaseResponseRecord;

public class HoursRecord implements BaseResponseRecord {

    private long day;
    private long fromT;
    private long toT;

    @Override
    public void setFieldsFromJSONObject(JSONObject ob) {
        this.day = (Long) ob.get("day");
        this.fromT = (Long) ob.get("fromT");
        this.toT = (Long) ob.get("toT");
    }

    public long getDay() {
        return day;
    }

    public long getFromT() {
        return fromT;
    }

    public long getToT() {
        return toT;
    }

	@Override
	public String toString() {
		return "HoursRecord [day=" + day + ", fromT=" + fromT + ", toT=" + toT + "]";
	}
}
