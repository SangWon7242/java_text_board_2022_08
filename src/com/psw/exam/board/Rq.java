package com.psw.exam.board;

import java.util.Map;

public class Rq {
  private String url; // 접근제어자를 붙이는게 관례. 외부에서 접근 불가능.
  private String urlPath;
  private Map<String, String> params;
  // 인스턴스 변수 -> 여기에 다 붙임

  // 필드추가가능

  // 수정가능
  Rq(String url) {
    this.url = url;
    urlPath = Util.getUrlPathFromUrl(this.url);
    params = Util.getParamsFromUrl(this.url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public int getIntParam(String paramName, int defaultValue) {

    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {

    if (params.containsKey(paramName) == false) {
      return defaultValue;
    }

    return params.get(paramName);
  }

  public String getUrlPath() {
    return urlPath;
  }

  public void setSessionAttr(String key, Member value) {
    Session session = Container.getSession();

    session.setAttribute(key, value);
  }
}
