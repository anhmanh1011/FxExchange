package com.api.orderfx.Utils;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@UtilityClass
public class SortUtils {

    public static int augmentCompare(Double o1, Double o2) {
      return o1.compareTo(o2);
    }
    public static int reduceCompare(Double o1, Double o2) {
      return o1.compareTo(o2);
    }
}
