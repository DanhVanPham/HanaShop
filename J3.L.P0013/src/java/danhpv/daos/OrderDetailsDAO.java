/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Food;
import danhpv.dtos.Order;
import danhpv.dtos.OrderDetail;
import danhpv.dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author DELL
 */
public class OrderDetailsDAO {

    private static Connection connection;
    private static PreparedStatement preStm;
    private static ResultSet resultSet;

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

    public OrderDetailsDAO() {
    }

    public boolean createNewOrderAndOrderDetails(HashMap<String, Food> cart, User user, Order order) throws Exception {
        boolean checkCreateOrderDetails;
        OrderDetailsDAO orderDetailsDao = new OrderDetailsDAO();
        FoodDAO foodDao = new FoodDAO();
        int countOrderItemId;
        for (Food food : cart.values()) {
            String orderItemId = orderDetailsDao.getLastOrderDetail();
            if (orderItemId == null) {
                countOrderItemId = 1;
                orderItemId = "ORDER-DETAILS-" + String.format("%05d", countOrderItemId);
            } else {
                String[] split = orderItemId.split("-");
                countOrderItemId = Integer.parseInt(split[2]);
                countOrderItemId += 1;
                orderItemId = split[0] + "-" + split[1] + "-" + String.format("%05d", countOrderItemId);
            }
            int quantity = foodDao.getQuantityByFoodId(food.getFoodId());
            if (food.getFoodQuantity() <= quantity && food.getFoodQuantity() > 0) {
                OrderDetail orderDetail = new OrderDetail(orderItemId, food.getFoodId(), order.getOrderId(), food.getFoodQuantity(), food.getFoodPrice(), new Date(), true);
                checkCreateOrderDetails = orderDetailsDao.createNewOrderDetail(orderDetail);
                if (checkCreateOrderDetails) {
                    food.setFoodQuantity(quantity - food.getFoodQuantity());
                    foodDao.updateFoodQuantityByFoodExisted(food);
                }
            }
        }
        return true;
    }

    public boolean createNewOrderDetail(OrderDetail orderDetail) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Insert into OrderDetail values(?, ?, ?, ?, ?, ?, ?)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, orderDetail.getOrderItemId());
                preStm.setString(2, orderDetail.getFoodItemId());
                preStm.setString(3, orderDetail.getOrderId());
                preStm.setInt(4, orderDetail.getFoodQuantity());
                preStm.setFloat(5, orderDetail.getFoodPrice());
                preStm.setTimestamp(6, new Timestamp(orderDetail.getCreateAt().getTime()));
                preStm.setBoolean(7, orderDetail.isStatus());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public String getLastOrderDetail() throws Exception {
        String orderItemId = null;
        try {
            String sql = "select orderItemId from OrderDetail where createAt = (select max(createAt) from OrderDetail)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    orderItemId = resultSet.getString("orderItemId");
                }
            }
        } finally {
            closeConnection();
        }
        return orderItemId;
    }

    public List<OrderDetail> getListOrderDetailByOrderId(String orderId) throws Exception {
        List<OrderDetail> listOrderDetails = null;
        try {
            String sql = "select orderItemId, orderDetail.foodId, foodName, food.imageUrl, "
                    + "orderDetail.foodQuantity, orderDetail.foodPrice, createAt "
                    + "from OrderDetail orderDetail join Food food on orderDetail.foodId = food.foodId\n"
                    + " where orderId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, orderId);
                resultSet = preStm.executeQuery();
                listOrderDetails = new ArrayList<>();
                while (resultSet.next()) {
                    String orderItemId = resultSet.getString("orderItemId");
                    String foodId = resultSet.getString("foodId");
                    String foodName = resultSet.getString("foodName");
                    String imageUrl = resultSet.getString("imageUrl");
                    int quantity = resultSet.getInt("foodQuantity");
                    float foodPrice = resultSet.getFloat("foodPrice");
                    Date createAt = new java.util.Date(resultSet.getTimestamp("createAt").getTime());
                    OrderDetail orderDetail = new OrderDetail(orderItemId, foodId, foodName, imageUrl, orderId, quantity, foodPrice, createAt);
                    listOrderDetails.add(orderDetail);
                }
            }
        } finally {
            closeConnection();
        }
        return listOrderDetails;
    }
}
