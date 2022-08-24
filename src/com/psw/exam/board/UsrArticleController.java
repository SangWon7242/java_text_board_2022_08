package com.psw.exam.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsrArticleController {
  int articleLastId;
  List<Article> articles;

  UsrArticleController() {
    articleLastId = 0;
    articles = new ArrayList<>();

    makeTestData();

    if (articles.size() > 0) {
      articleLastId = articles.get(articles.size() - 1).id;
    }
  }

  void makeTestData() {
    for (int i = 0; i < 100; i++) {
      int id = i + 1;
      articles.add(new Article(id, "제목" + id, "내용" + id));
    }
  }

  public void actionDelete(Rq rq) {
   int id = rq.getIntParam("id", 0);

   if ( id == 0) {
     System.out.println("id를 올바르게 입력해주세요.");
     return;
   }

    Article foundArticle = null;

    for (Article article : articles) {
      if (article.id == id) {
        foundArticle = article;
        break;
      }
    }

    if (foundArticle == null) {
      System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(foundArticle);

    System.out.printf("%d번 게시물을 삭제하였습니다.\n", id);

  }

  public void actionModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if ( id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article foundArticle = null;

    for (Article article : articles) {
      if (article.id == id) {
        foundArticle = article;
        break;
      }
    }

    if (foundArticle == null) {
      System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    foundArticle.title = Container.sc.nextLine();
    System.out.printf("새 내용 : ");
    foundArticle.body = Container.sc.nextLine();

    System.out.printf("%d번 게시물을 수정하였습니다.\n", id);
  }

  public void actionWrite() {
    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = Container.sc.nextLine();
    System.out.printf("내용 : ");
    String body = Container.sc.nextLine();

    int id = articleLastId + 1;
    articleLastId = id;

    Article article = new Article(id, title, body);

    articles.add(article);
    System.out.println("생성 된 게시물 객체 : " + article);
    System.out.printf("%d번 게시물이 입력 되었습니다.\n", article.id);
  }

  public void actionDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if ( id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articles.get(id - 1);

    if (id > articles.size()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("== 게시물 상세내용 ==");
    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.body);
  }

  public void actionList(Rq rq) {
    System.out.println("== 게시물 리스트 ==");
    System.out.println("-------------------");
    System.out.println("번호 / 제목 / 내용");
    System.out.println("-------------------");

    Map<String, String> params = rq.getParams();

    // 검색시작
    List<Article> fileredArticles = articles;

    if (params.containsKey("searchKeyword")) {
      String searchKeyword = rq.getParam("searchKeyword", "");

      fileredArticles = new ArrayList<>();

      if ( searchKeyword.length() > 0 ) {
        for (Article article : articles) {
          boolean matched = article.title.contains(searchKeyword) || article.body.contains(searchKeyword);

          if (matched) {
            fileredArticles.add(article);
          }
        }
      }
    }
    // 검색 끝

    List<Article> sortedArticles = fileredArticles;

    String orderBy = rq.getParam("orderBy", "idDesc");

    boolean orderByIdDesc = orderBy.equals("idDesc");

    if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
      orderByIdDesc = false;
    }

    if (orderByIdDesc) {
      sortedArticles = Util.reverseList(sortedArticles);
    }

    for (Article article : sortedArticles) {
      System.out.printf("%d / %s / %s\n", article.id, article.title, article.body);
    }

  }
}