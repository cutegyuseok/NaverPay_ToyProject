package org.example.naverpay.member.dao;

import org.example.naverpay.member.database.JDBCMgr;
import org.example.naverpay.member.entity.Members;
import org.example.naverpay.member.entity.Shopping;
import org.example.naverpay.member.service.ShoppingService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ShoppingDAO implements iShoppingDAO{

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;
    private static ShoppingDAO shoppingDAO = null;

    private static final String SHOPPING_INFO_LIST_SELECT_BY_PERIOD = "SELECT * FROM shopping WHERE mId = ? AND sDate >=? ORDER BY sDate DESC";
    private static final String SHOPPING_INFO_SELECT_BY_SID = "SELECT * FROM shopping where sId =?";
    private static final String SHOPPING_INFO_DELETE_BY_SID =  "DELELTE * FROM shopping WHERE sId = ?";
    public static ShoppingDAO getInstance() {
        if (shoppingDAO == null) {
            shoppingDAO = new ShoppingDAO();
        }
        return shoppingDAO;
    }

    @Override
    public Shopping select(String sId) {
        Shopping shopping = null;
        try {
            conn = JDBCMgr.getConnection();
            stmt = conn.prepareStatement(SHOPPING_INFO_SELECT_BY_SID);
            stmt.setString(1, sId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String mId = rs.getString("mId");
                String sTitle = rs.getString("sTitle");
                int sCount = rs.getInt("sCount");
                int sPayment = rs.getInt("sPayment");
                String sDate = rs.getString("sDate");
                String sStatus = rs.getString("sStatus");
                String seller = rs.getString("seller");
                String sellerPhoneNumber = rs.getString("sellerPhoneNumber");
                shopping = new Shopping(mId, sId, sTitle, sCount, sPayment, sDate, sStatus,seller,sellerPhoneNumber);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCMgr.close(rs, stmt, conn);
        }
        return shopping;
    }

    @Override
    public List<Shopping> selectAll(String mId, String period) {
        List<Shopping> shoppingList = new LinkedList<>();
        try {
            conn = JDBCMgr.getConnection();
            stmt = conn.prepareStatement(SHOPPING_INFO_LIST_SELECT_BY_PERIOD);
            stmt.setString(1, mId);
            stmt.setString(2, period);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String sId = rs.getString("sId");
                String sTitle = rs.getString("sTitle");
                int sCount = rs.getInt("sCount");
                int sPayment = rs.getInt("sPayment");
                String sDate = rs.getString("sDate");
                String sStatus = rs.getString("sStatus");
                String seller = rs.getString("seller");
                String sellerPhoneNumber = rs.getString("sellerPhoneNumber");

                shoppingList.add(new Shopping(mId, sId, sTitle, sCount, sPayment, sDate, sStatus,seller,sellerPhoneNumber));
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            JDBCMgr.close(rs, stmt, conn);
        }
        return shoppingList;
    }

    @Override
    public int delete(String sId) {
        int res = 0;
        try {
            conn = JDBCMgr.getConnection();
            stmt = conn.prepareStatement(SHOPPING_INFO_DELETE_BY_SID);
            stmt.setString(1, sId);
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCMgr.close(stmt, conn);
        }
        return res;
    }
}
