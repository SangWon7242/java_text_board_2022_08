package com.psw.exam.board.service;

import com.psw.exam.board.dto.Member;
import com.psw.exam.board.repository.MemberRepository;

public class MemberService {
  private MemberRepository memberRepository;

  public MemberService() {
    memberRepository = new MemberRepository();
  }
  public void makeTestData() {
    for (int i = 0; i < 3; i++) {
      String loginId = "user" + (i + 1);
      String loginPw = "user" + (i + 1);

      join(loginId, loginPw);
    }
  }

  public int join(String loginId, String loginPw) {
    return memberRepository.join(loginId, loginPw);
  }

  public Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }
}
