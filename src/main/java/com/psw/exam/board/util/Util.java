package com.psw.exam.board.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {

  public static Map<String, String> getParamsFromUrl(String url) {
    Map<String, String> params = new HashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if (urlBits.length == 1) {
      return params;
    }

    String queryStr = urlBits[1];
    for (String bit : queryStr.split("&")) {
      String[] bits = bit.split("=", 2);
      if (bits.length == 1) {
        continue;
      }
      params.put(bits[0], bits[1]);
    }

    return params;
  }

  public static String getUrlPathFromUrl(String url) {
    return url.split("\\?", 2)[0];
  }

  public static<T> List<T> reverseList(List<T> list) {
    List<T> reverse = new ArrayList<>(list.size());

    for ( int i = list.size() - 1; i >= 0; i-- ) {
      reverse.add(list.get(i));
    }
    return reverse;
  }

  public static String getNowDateStr() {
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String dateStr = format1.format(System.currentTimeMillis());

    return dateStr;
  }
}
