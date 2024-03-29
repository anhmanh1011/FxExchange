package api.message.response;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import api.message.error.APIReplyParseException;
import api.message.records.CalendarRecord;
import api.message.response.APIErrorResponse;
import api.message.response.BaseResponse;

public class CalendarResponse extends BaseResponse {

    private List<CalendarRecord> calendarRecords = new LinkedList<CalendarRecord>();

    @SuppressWarnings("rawtypes")
    public CalendarResponse(String body) throws APIReplyParseException, APIErrorResponse {
        super(body);
        JSONArray arr = (JSONArray) this.getReturnData();
        for (Iterator it = arr.iterator(); it.hasNext();) {
            JSONObject e = (JSONObject) it.next();
            CalendarRecord record = new CalendarRecord();
            record.setFieldsFromJSONObject(e);
            this.calendarRecords.add(record);
        }
    }

    public List<CalendarRecord> getCalendarRecords() {
        return calendarRecords;
    }

    @Override
    public String toString() {
    	String response = "CalendarResponse{calendarRecords=[";
    	for (CalendarRecord ri : calendarRecords) {
    		response += ri.toString() + ",";
    	}
    	response += "]}";
        return response;
    }
}
