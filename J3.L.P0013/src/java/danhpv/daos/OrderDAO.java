/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Order;
import danhpv.dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
public class OrderDAO {

    private Connection connection;
    private PreparedStatement preStm;
    private ResultSet resultSet;

    private void closeConnection() throws Exception {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public OrderDAO() {
    }

    public boolean createNewOrder(Order order) throws Exception {
        boolean checkSuccess = false;
        String sql = "Insert into Orders values(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, order.getOrderId());
                preStm.setString(2, order.getUserId());
                preStm.setString(3, order.getFullName());
                preStm.setString(4, order.getPhoneNumber());
                preStm.setString(5, order.getAddress());
                preStm.setTimestamp(6, new Timestamp(order.getCreateAt().getTime()));
                preStm.setFloat(7, order.getOrderTotal());
                preStm.setInt(8, order.getStatus());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public String getLastOrderRecord() throws Exception {
        String foodId = null;
        String sql = "select orderId from Orders where createAt = (select max(createAt) from Orders)";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    foodId = resultSet.getString("orderId");
                }
            }
        } finally {
            closeConnection();
        }
        return foodId;
    }

    public List<Order> viewListHistoryOrdersByUserId(User user, String searchByFoodName, String searchByDate, String nextDate) throws Exception {
        List<Order> listHistoryOrders = null;
        try {
            String sql = "select orderId, userId, createAt, orderTotal from Orders "
                    + "where orderId in (select orderDetail.orderId from OrderDetail orderDetail join Food food on orderDetail.foodId = food.foodId\n"
                    + "	join Orders orders on orderDetail.orderId = orders.orderId\n"
                    + "	where foodName like ?) and (userId = ?) and ((? is null and ? is null) or (createAt >= ? and createAt < ?)) "
                    + " and status = ? order by createAt desc";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, "%" + searchByFoodName + "%");
                preStm.setString(2, user.getUserId());
                preStm.setString(3, searchByDate);
                preStm.setString(4, nextDate);
                preStm.setString(5, searchByDate);
                preStm.setString(6, nextDate);
                preStm.setInt(7, 1);
                resultSet = preStm.executeQuery();
                listHistoryOrders = new ArrayList<>();
                while (resultSet.next()) {
                    String orderId = resultSet.getString("orderId");
                    Date date = new java.util.Date(resultSet.getTimestamp("createAt").getTime());
                    Float orderTotal = resultSet.getFloat("orderTotal");
                    Order order = new Order();
                    order.setOrderId(orderId);
                    order.setCreateAt(date);
                    order.setOrderTotal(orderTotal);
                    listHistoryOrders.add(order);
                }
            }
        } finally {
            closeConnection();
        }
        return listHistoryOrders;
    }

    public List<Order> viewListHistoryOrdersOfUserByAdmin(String searchByFoodName, String searchByDate, String nextDate) throws Exception {
        List<Order> listHistoryOrders = null;
        try {
            String sql = "select orderId, userId, createAt, orderTotal from Orders "
                    + "where orderId in (select orderDetail.orderId from OrderDetail orderDetail join Food food on orderDetail.foodId = food.foodId\n"
                    + "	join Orders orders on orderDetail.orderId = orders.orderId\n"
                    + "	where foodName like ?) and ((? is null and ? is null) or (createAt >= ? and createAt < ?)) order by createAt desc";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, "%" + searchByFoodName + "%");
                preStm.setString(2, searchByDate);
                preStm.setString(3, nextDate);
                preStm.setString(4, searchByDate);
                preStm.setString(5, nextDate);
                resultSet = preStm.executeQuery();
                listHistoryOrders = new ArrayList<>();
                while (resultSet.next()) {
                    String orderId = resultSet.getString("orderId");
                    String userId = resultSet.getString("userId");
                    Date date = new java.util.Date(resultSet.getTimestamp("createAt").getTime());
                    Float orderTotal = resultSet.getFloat("orderTotal");
                    Order order = new Order();
                    order.setOrderId(orderId);
                    order.setUserId(userId);
                    order.setCreateAt(date);
                    order.setOrderTotal(orderTotal);
                    listHistoryOrders.add(order);
                }
            }
        } finally {
            closeConnection();
        }
        return listHistoryOrders;
    }
}
