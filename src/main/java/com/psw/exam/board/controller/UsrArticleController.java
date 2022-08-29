package com.psw.exam.board.controller;

import com.psw.exam.board.dto.Article;
import com.psw.exam.board.Rq;
import com.psw.exam.board.container.Container;
import com.psw.exam.board.service.ArticleService;
import com.psw.exam.board.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsrArticleController {
  private ArticleService articleService;
  private List<Article> articles;

  public UsrArticleController() {
    articleService = Container.getArticleService();
    makeTestData();
  }

  void makeTestData() {
    articleService.makeTestData();
  }

  public void actionDelete(Rq rq) {
   int id = rq.getIntParam("id", 0);

   if ( id == 0) {
     System.out.println("id를 올바르게 입력해주세요.");
     return;
   }

    Article foundArticle = null;

    for (Article article : articles) {
      if (article.getId() == id) {
        foundArticle = article;
        break;
      }
    }

    if (foundArticle == null) {
      System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    articleService.deleteArticleById(id);

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
      if (article.getId() == id) {
        foundArticle = article;
        break;
      }
    }

    if (foundArticle == null) {
      System.out.printf("%d번 게시물이 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    foundArticle.setTitle(Container.getSc().nextLine());
    System.out.printf("새 내용 : ");
    foundArticle.setBody(Container.getSc().nextLine());

    System.out.printf("%d번 게시물을 수정하였습니다.\n", id);
  }

  public void actionWrite() {
    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = Container.getSc().nextLine();
    System.out.printf("내용 : ");
    String body = Container.getSc().nextLine();

    int id = articleService.write(title, body);

    System.out.printf("%d번 게시물이 입력 되었습니다.\n", id);
  }

  public void actionDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if ( id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("== 게시물 상세내용 ==");
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("제목 : %s\n", article.getTitle());
    System.out.printf("내용 : %s\n", article.getBody());
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
          boolean matched = article.getTitle().contains(searchKeyword) || article.getBody().contains(searchKeyword);

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
      System.out.printf("%d / %s / %s\n", article.getId(), article.getTitle(), article.getBody());
    }

  }
}
