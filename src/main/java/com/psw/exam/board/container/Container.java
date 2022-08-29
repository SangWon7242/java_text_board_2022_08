package com.psw.exam.board.container;

import com.psw.exam.board.Session;
import com.psw.exam.board.controller.UsrArticleController;
import com.psw.exam.board.controller.UsrMemberController;
import com.psw.exam.board.interceptor.NeedLoginInterceptor;
import com.psw.exam.board.interceptor.NeedLogoutInterceptor;
import com.psw.exam.board.repository.ArticleRepository;
import com.psw.exam.board.repository.MemberRepository;
import com.psw.exam.board.service.ArticleService;
import com.psw.exam.board.service.MemberService;
import lombok.Getter;

import java.util.Scanner;

public class Container {
  @Getter
  private static Scanner sc;
  @Getter
  private static Session session;
  @Getter
  private static MemberRepository memberRepository;
  @Getter
  private static ArticleRepository articleRepository;

  @Getter
  private static NeedLoginInterceptor needLoginInterceptor;
  @Getter
  private static NeedLogoutInterceptor needLogoutInterceptor;
  @Getter
  private static ArticleService articleService;

  @Getter
  private static MemberService memberService;
  @Getter
  private static UsrArticleController usrArticleController;
  @Getter
  private static UsrMemberController usrMemberController;

  static {
    sc = new Scanner(System.in);
    session = new Session();

    articleRepository = new ArticleRepository();
    memberRepository = new MemberRepository();

    needLoginInterceptor = new NeedLoginInterceptor();
    needLogoutInterceptor = new NeedLogoutInterceptor();

    articleService = new ArticleService();
    memberService = new MemberService();

    usrArticleController = new UsrArticleController();
    usrMemberController = new UsrMemberController();
  }

  public static Session getSession() {
    return session;
  }

}
