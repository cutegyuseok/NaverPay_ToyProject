package org.example.naverpay.member.dao;

import org.example.naverpay.member.entity.Shopping;

import java.util.List;

public interface iShoppingDAO {

    Shopping select(String sId); // 특정 구매 내역 반환

    List<Shopping> selectAll(String mid, String period); // 기간 동안 구매 내역 모두 반환

    int delete(String sId); // 구매 내역 삭제
}
