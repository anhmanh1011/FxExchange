package com.api.orderfx.Utils;

import lombok.experimental.UtilityClass;
import org.json.JSONObject;

@UtilityClass
public class JsonUtils {

    public String ObjectToJson(Object obj) {
        return JSONObject.valueToString(obj);
    }

}
