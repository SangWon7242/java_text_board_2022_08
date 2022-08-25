package com.psw.exam.board;

import com.psw.exam.board.container.Container;
import com.psw.exam.board.dto.Member;
import com.psw.exam.board.util.Util;

import java.util.Map;

public class Rq {
  private String url; // 접근제어자를 붙이는게 관례. 외부에서 접근 불가능.
  private String urlPath;
  private Map<String, String> params;
  public Rq() {

  }
  public Rq(String url) {
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

  public Member getLoginedMember() {
    return (Member) getSessionAttr("loginedMember");
  }

  private Object getSessionAttr(String key) {
    Session session = Container.getSession();

    return session.getAttribute(key);
  }

  public void setSessionAttr(String key, Member value) {
    Session session = Container.getSession();

    session.setAttribute(key, value);
  }

  public void removeSessionAttr(String key) {
    Session session = Container.getSession();

    session.removeAttribute(key);
  }

  public void setCommand(String url) {
    urlPath = Util.getUrlPathFromUrl(url);
    params = Util.getParamsFromUrl(url);
  }

  public boolean isLogined() {
    return hasSessionAttr("loginedMember");
  }

  public boolean hasSessionAttr(String key) {
    Session session = Container.getSession();

    return session.hasAttribute(key);
  }

  public void logout() {
    removeSessionAttr("loginedMember");
  }

  public void login(Member member) {
    setSessionAttr("loginedMember", member);
  }
}
