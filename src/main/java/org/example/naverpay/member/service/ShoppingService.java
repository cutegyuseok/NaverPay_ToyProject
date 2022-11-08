package org.example.naverpay.member.service;

import org.example.naverpay.member.dao.MembersDAO;
import org.example.naverpay.member.dao.ShoppingDAO;
import org.example.naverpay.member.dto.ShoppingDTO;
import org.example.naverpay.member.entity.Shopping;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingService implements iShoppingService {

    private ShoppingDAO shoppingDAO = ShoppingDAO.getInstance();

    public static ShoppingService shoppingService = null;

    public static ShoppingService getInstance(){
        if(shoppingService == null){
            shoppingService = new ShoppingService();
        }
        return shoppingService;
    }
    @Override
    public List<ShoppingDTO> getShoppingList(String mId,String period) {
        if(mId==null || period == null) return null;
        List<Shopping> shoppingList = shoppingDAO.selectAll(mId,period);
        List<ShoppingDTO> list = new LinkedList<>();
        for(int i=0; i<shoppingList.size(); i++){
            list.add(shoppingList.get(i).toDTO());
        }
        return list;
    }

    @Override
    public ShoppingDTO getShoppingInfo(String sId) {
        Shopping shopping = shoppingDAO.select(sId);
        ShoppingDTO shoppingDTO = shopping.toDTO();
        return shoppingDTO;
    }

    @Override
    public void deleteShoppingList(String sId) {
        int res = shoppingDAO.delete(sId);
    }
}
