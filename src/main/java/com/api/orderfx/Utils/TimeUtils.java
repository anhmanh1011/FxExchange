package com.api.orderfx.Utils;

import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class TimeUtils {

    public Long getLongTime(int day) {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, day);
        dt = c.getTime();
        return dt.getTime();
    }
}
