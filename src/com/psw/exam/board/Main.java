package com.psw.exam.board;

import java.util.*;

public class Main {
  static void makeTestData(List<Article> articles) {
    articles.add(new Article(1, "제목1", "내용1"));
    articles.add(new Article(2, "제목2", "내용2"));
    articles.add(new Article(3, "제목3", "내용3"));
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int articleLastId = 0;

    List<Article> articles = new ArrayList<>();

    makeTestData(articles);

    if ( articles.size() > 0) {
      articleLastId = articles.get(articles.size() - 1).id;
    }


    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");

    while(true) {
      System.out.printf("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);
      Map<String, String> params = rq.getParams();

      if(rq.getUrlPath().equals("exit")) {
        break;
      }
      else if (rq.getUrlPath().equals("/usr/article/list")) {
        System.out.println("== 게시물 리스트 ==");
        System.out.println("-------------------");
        System.out.println("번호 / 제목 / 내용");

        for ( int i = articles.size() - 1; i >= 0; i--) {
          Article article = articles.get(i);
          System.out.printf("%d / %s / %s\n", article.id, article.title, article.body);
        }

        System.out.println("-------------------");
      }
      else if(rq.getUrlPath().equals("/usr/article/write")) {
        System.out.println("== 게시물 등록 ==");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();
        int id = articleLastId + 1;
        articleLastId = id;

        Article article = new Article(id, title, body);

        articles.add(article);
        System.out.println("생성 된 게시물 객체 : " + article);
        System.out.printf("%d번 게시물이 입력 되었습니다.\n", article.id);
      }
      else if (rq.getUrlPath().equals("/usr/article/detail")) {

        int id = Integer.parseInt(params.get("id"));

        Article article = articles.get(id - 1);

        if( id > articles.size() ) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("== 게시물 상세내용 ==");
        System.out.printf("번호 : %d\n", article.id);
        System.out.printf("제목 : %s\n", article.title);
        System.out.printf("내용 : %s\n", article.body);
      }

      else {
        System.out.printf("입력 된 명령어 : %s\n", cmd);
      }
    }
    System.out.println("== 프로그램 종료 ==");

    sc.close();
  }
}

class Article {
  int id;
  String title;
  String body;

  Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("{id : %d, title : \"%s\", body : \"%s\"}", id, title, body);
  }
}

class Rq {
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

  // 수정가능, if문 금지
  public Map<String, String> getParams() {
    return params;
  }

  // 수정가능, if문 금지
  public String getUrlPath() {
    return urlPath;
  }
}

// 수정불가능
class Util {
  static Map<String, String> getParamsFromUrl(String url) {
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

  static String getUrlPathFromUrl(String url) {
    return url.split("\\?", 2)[0];
  }
}