package com.psw.exam.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article {
  private int id;
  private int bordId;
  private String title;
  private String body;
}

