package com.psw.exam.board.repository;

import com.psw.exam.board.dto.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
  private List<Article> articles;
  private int lastId;

  public ArticleRepository() {
    lastId = 0;
    articles = new ArrayList<>();
  }

  public int write(int boardId, String title, String body) {
    int id = lastId + 1;
    Article article = new Article(id, boardId, title, body);
    articles.add(article);
    lastId = id;

    return id;
  }

  public List<Article> getArticles() {
    return articles;
  }

  public Article getArticleById(int id) {
    for ( Article article : articles ) {
      if (article.getId() == id ) {
        return article;
      }
    }
    return null;
  }

  public void deleteArticleById(int id) {
    Article article = getArticleById(id);

    if ( article != null ) {
      articles.remove(article);
    }
  }
}
