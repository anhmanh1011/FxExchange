package com.api.orderfx.Utils;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;
import org.json.JSONObject;

@UtilityClass
public class JsonUtils {
    Gson gson = new Gson();

    public String ObjectToJson(Object obj) {
        return JSONObject.valueToString(obj);
    }

    public <T> T JsonToObject(String json, Class<T> Class) {
        return gson.fromJson(json, Class);
    }

}
