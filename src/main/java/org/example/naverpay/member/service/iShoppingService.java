package org.example.naverpay.member.service;

import org.example.naverpay.member.dto.ShoppingDTO;

import java.util.List;

public interface iShoppingService {

    List<ShoppingDTO> getShoppingList(String uId,String period);

    ShoppingDTO getShoppingInfo(String sId);

    void deleteShoppingList(String sId);

}
