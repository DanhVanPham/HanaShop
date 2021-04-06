/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Food;
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
public class FoodDAO {

    private static Connection connection;
    private static PreparedStatement preStm;
    private static ResultSet resultSet;

    private static void closeConnection() throws Exception {
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

    public FoodDAO() {
    }

    public String getLastFoodAdded() throws Exception {
        String foodId = null;
        try {
            String sql = "select foodId from Food where createDate = (select max(createDate) from Food)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    foodId = resultSet.getString("foodId");
                }
            }
        } finally {
            closeConnection();
        }
        return foodId;
    }

    public Food getFoodByFoodId(String foodId) throws Exception {
        Food food = null;
        try {
            String sql = "select foodName, foodPrice, foodQuantity, createDate, imageUrl, status, foodDescription, categoryId from Food where foodId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, foodId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String foodName = resultSet.getString("foodName");
                    float foodPrice = resultSet.getFloat("foodPrice");
                    int quantity = resultSet.getInt("foodQuantity");
                    Date date = new java.util.Date(resultSet.getTimestamp("createDate").getTime());
                    String categoryId = resultSet.getString("categoryId");
                    String foodDescription = resultSet.getString("foodDescription");
                    String imageUrl = resultSet.getString("imageUrl");
                    boolean status = resultSet.getBoolean("status");
                    food = new Food();
                    food.setFoodId(foodId);
                    food.setFoodName(foodName);
                    food.setFoodPrice(foodPrice);
                    food.setFoodQuantity(quantity);
                    food.setFoodDescription(foodDescription);
                    food.setCategoryId(categoryId);
                    food.setImageUrl(imageUrl);
                    food.setCreateDate(date);
                    food.setStatus(status);
                }
            }
        } finally {
            closeConnection();
        }
        return food;
    }

    public List<Food> getListFoodsWithPagningByUser(int indexStart, int pageSize) throws Exception {
        List<Food> listFoods = null;
        try {
            String sql = "Select foodId, categoryId, foodName ,foodPrice, imageUrl, foodDescription from ("
                    + "(select * from Food where status = ? and foodQuantity > ?)) as FoodTable"
                    + " order by createDate DESC offset ? rows"
                    + " fetch next ? rows only";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, "True");
                preStm.setInt(2, 0);
                preStm.setInt(3, indexStart);
                preStm.setInt(4, pageSize);
                resultSet = preStm.executeQuery();
                listFoods = new ArrayList<>();
                while (resultSet.next()) {
                    String foodId = resultSet.getString("foodId");
                    String categoryId = resultSet.getString("categoryId");
                    String foodName = resultSet.getString("foodName");
                    float foodPrice = resultSet.getFloat("foodPrice");
                    String imageUrl = resultSet.getString("imageUrl");
                    String foodDescription = resultSet.getString("foodDescription");
                    Food food = new Food(foodId, categoryId, foodName, foodPrice, foodDescription);
                    food.setImageUrl(imageUrl);
                    listFoods.add(food);
                }
            }
        } finally {
            closeConnection();
        }
        return listFoods;
    }

    public List<Food> getListFoodsWithPagningByAdmin(int indexStart, int pageSize) throws Exception {
        List<Food> listFoods = null;
        try {
            String sql = "Select foodId, categoryId, foodName ,foodPrice, foodQuantity, foodDescription, createDate, status from Food "
                    + " order by createDate DESC offset ? rows"
                    + " fetch next ? rows only";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setInt(1, indexStart);
                preStm.setInt(2, pageSize);
                resultSet = preStm.executeQuery();
                listFoods = new ArrayList<>();
                while (resultSet.next()) {
                    String foodId = resultSet.getString("foodId");
                    String categoryId = resultSet.getString("categoryId");
                    String foodName = resultSet.getString("foodName");
                    float foodPrice = resultSet.getFloat("foodPrice");
                    int foodQuantity = resultSet.getInt("foodQuantity");
                    Date createDate = new java.util.Date(resultSet.getTimestamp("createDate").getTime());
                    String foodDescription = resultSet.getString("foodDescription");
                    boolean status = resultSet.getBoolean("status");
                    Food food = new Food(foodId, categoryId, foodName, foodPrice, foodDescription);
                    food.setStatus(status);
                    food.setCreateDate(createDate);
                    food.setFoodQuantity(foodQuantity);
                    listFoods.add(food);
                }
            }
        } finally {
            closeConnection();
        }
        return listFoods;
    }

    public long getCountAllFoodsByUser() throws Exception {
        long count = 0;
        try {
            String sql = "Select count(foodId) as 'COUNT' from Food where status = ? "
                    + "and foodQuantity > ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, "True");
                preStm.setInt(2, 0);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public long getCountAllFoodsByAdmin() throws Exception {
        long count = 0;
        try {
            String sql = "Select count(foodId) as 'COUNT' from Food";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public long getCountAllFoodsBySearchWithUser(String nameSearch, String category, String moneyFrom, String moneyTo) throws Exception {
        long count = 0;
        try {
            String sql = "Select count (foodId) as 'COUNT' from Food Where status = ? "
                    + "and (? is null or foodName like ?) and (? is null or categoryId like ?) and "
                    + "((? is null and ? is null) or (foodPrice >= ? and foodPrice <= ?)) and foodQuantity > ? ";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setInt(1, 1);
                preStm.setString(2, nameSearch);
                preStm.setString(3, "%" + nameSearch + "%");
                preStm.setString(4, category);
                preStm.setString(5, category);
                preStm.setString(6, moneyFrom);
                preStm.setString(7, moneyTo);
                preStm.setString(8, moneyFrom);
                preStm.setString(9, moneyTo);
                preStm.setInt(10, 0);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public List<Food> getPagingBySearchWithUser(String nameSearch, String category, String moneyFrom, String moneyTo, int indexStart, int pageSize) throws Exception {
        List<Food> listFoods = null;
        try {
            String sql = "Select foodId, categoryId, foodName ,foodPrice, foodQuantity, imageUrl, foodDescription from ("
                    + "(select * from Food where status = ? "
                    + "and (? is null or foodName like ?) and (? is null or categoryId = ?) "
                    + "and ((? is null and ? is null) or (foodPrice >= ? and foodPrice <= ?)) and foodQuantity > ?)) as FoodTable"
                    + " order by createDate DESC offset ? rows"
                    + " fetch next ? rows only";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, "True");
                preStm.setString(2, nameSearch);
                preStm.setString(3, "%" + nameSearch + "%");
                preStm.setString(4, category);
                preStm.setString(5, category);
                preStm.setString(6, moneyFrom);
                preStm.setString(7, moneyTo);
                preStm.setString(8, moneyFrom);
                preStm.setString(9, moneyTo);
                preStm.setInt(10, 0);
                preStm.setInt(11, indexStart);
                preStm.setInt(12, pageSize);
                resultSet = preStm.executeQuery();
                listFoods = new ArrayList<>();
                while (resultSet.next()) {
                    String foodId = resultSet.getString("foodId");
                    String categoryId = resultSet.getString("categoryId");
                    String foodName = resultSet.getString("foodName");
                    float foodPrice = resultSet.getFloat("foodPrice");
                    int foodQuantity = resultSet.getInt("foodQuantity");
                    String imageUrl = resultSet.getString("imageUrl");
                    String foodDescription = resultSet.getString("foodDescription");
                    Food food = new Food(foodId, categoryId, foodName, foodPrice, foodDescription);
                    food.setFoodQuantity(foodQuantity);
                    food.setImageUrl(imageUrl);
                    listFoods.add(food);
                }
            }
        } finally {
            closeConnection();
        }
        return listFoods;
    }

    public long getCountAllFoodsBySearchWithAdmin(String nameSearch, String category, String moneyFrom, String moneyTo) throws Exception {
        long count = 0;
        try {
            String sql = "Select count (foodId) as 'COUNT' from Food Where (? is null or foodName like ?) and (? is null or categoryId like ?) and "
                    + "((? is null and ? is null) or (foodPrice >= ? and foodPrice <= ?)) ";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, nameSearch);
                preStm.setString(2, "%" + nameSearch + "%");
                preStm.setString(3, category);
                preStm.setString(4, category);
                preStm.setString(5, moneyFrom);
                preStm.setString(6, moneyTo);
                preStm.setString(7, moneyFrom);
                preStm.setString(8, moneyTo);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public List<Food> getPagingBySearchWithAdmin(String nameSearch, String category, String moneyFrom, String moneyTo, int indexStart, int pageSize) throws Exception {
        List<Food> listFoods = null;
        try {
            String sql = "Select foodId, categoryId, foodName ,foodPrice, foodQuantity, imageUrl, createDate, foodDescription, status from ("
                    + "(select * from Food where (? is null or foodName like ?) and (? is null or categoryId = ?) "
                    + "and ((? is null and ? is null) or (foodPrice >= ? and foodPrice <= ?)))) as FoodTable"
                    + " order by createDate DESC offset ? rows"
                    + " fetch next ? rows only";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, nameSearch);
                preStm.setString(2, "%" + nameSearch + "%");
                preStm.setString(3, category);
                preStm.setString(4, category);
                preStm.setString(5, moneyFrom);
                preStm.setString(6, moneyTo);
                preStm.setString(7, moneyFrom);
                preStm.setString(8, moneyTo);
                preStm.setInt(9, indexStart);
                preStm.setInt(10, pageSize);
                resultSet = preStm.executeQuery();
                listFoods = new ArrayList<>();
                while (resultSet.next()) {
                    String foodId = resultSet.getString("foodId");
                    String categoryId = resultSet.getString("categoryId");
                    String foodName = resultSet.getString("foodName");
                    float foodPrice = resultSet.getFloat("foodPrice");
                    int foodQuantity = resultSet.getInt("foodQuantity");
                    String imageUrl = resultSet.getString("imageUrl");
                    String foodDescription = resultSet.getString("foodDescription");
                    Date createDate = new java.util.Date(resultSet.getTimestamp("createDate").getTime());
                    boolean status = resultSet.getBoolean("status");
                    Food food = new Food(foodId, categoryId, foodName, foodPrice, foodDescription);
                    food.setStatus(status);
                    food.setFoodQuantity(foodQuantity);
                    food.setImageUrl(imageUrl);
                    food.setCreateDate(createDate);
                    listFoods.add(food);
                }
            }
        } finally {
            closeConnection();
        }
        return listFoods;
    }

    public boolean removeFood(Food food) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "UPDATE Food "
                    + "SET status = ? "
                    + "WHERE foodId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setBoolean(1, food.isStatus());
                preStm.setString(2, food.getFoodId());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public boolean createNewFood(Food newFood) throws Exception {
        boolean checkCreate = false;
        try {
            String sql = "Insert into Food values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, newFood.getFoodId());
                preStm.setString(2, newFood.getCategoryId());
                preStm.setString(3, newFood.getFoodName());
                preStm.setFloat(4, newFood.getFoodPrice());
                preStm.setInt(5, newFood.getFoodQuantity());
                preStm.setString(6, newFood.getImageUrl());
                preStm.setString(7, newFood.getFoodDescription());
                preStm.setTimestamp(8, new Timestamp(newFood.getCreateDate().getTime()));
                preStm.setBoolean(9, newFood.isStatus());
                checkCreate = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkCreate;
    }

    public boolean updateFoodByFoodExisted(Food food) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Update Food "
                    + "SET categoryId = ?,  foodName = ?, foodPrice = ?,  foodQuantity = ? "
                    + ", imageUrl = ?, foodDescription = ? "
                    + "Where foodId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, food.getCategoryId());
                preStm.setString(2, food.getFoodName());
                preStm.setFloat(3, food.getFoodPrice());
                preStm.setInt(4, food.getFoodQuantity());
                preStm.setString(5, food.getImageUrl());
                preStm.setString(6, food.getFoodDescription());
                preStm.setString(7, food.getFoodId());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public int getQuantityByFoodId(String foodId) throws Exception {
        int quantity = 0;
        try {
            String sql = "Select foodQuantity from Food where foodId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, foodId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    quantity = resultSet.getInt("foodQuantity");
                }
            }
        } finally {
            closeConnection();
        }
        return quantity;
    }

    public boolean updateFoodQuantityByFoodExisted(Food food) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Update Food "
                    + "SET foodQuantity = ? "
                    + "Where foodId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setInt(1, food.getFoodQuantity());
                preStm.setString(2, food.getFoodId());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public List<Food> getListFoodFavouritesByUserId(User user) throws Exception {
        List<Food> listFoods = null;
        try {
            String sql = "select TOP 5 orderDetail.foodId, food.foodName, food.imageUrl, "
                    + "count(orderDetail.foodId) as 'Count' from Orders orders join "
                    + "OrderDetail orderDetail on orders.orderId = orderDetail.orderId \n"
                    + "	join Food food on orderDetail.foodId = food.foodId "
                    + "where orders.userId = ? and food.status = ? group by orderDetail.foodId, food.foodName, "
                    + "food.imageUrl order by Count desc";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, user.getUserId());
                preStm.setInt(2, 1);
                resultSet = preStm.executeQuery();
                listFoods = new ArrayList<>();
                while (resultSet.next()) {
                    String foodId = resultSet.getString("foodId");
                    String foodName = resultSet.getString("foodName");
                    String imageUrl = resultSet.getString("imageUrl");
                    Food food = new Food();
                    food.setFoodId(foodId);
                    food.setFoodName(foodName);
                    food.setImageUrl(imageUrl);
                    listFoods.add(food);
                }
            }
        } finally {
            closeConnection();
        }
        return listFoods;
    }

    public List<Food> getListFoodsRelatedByFoodId(Food food) throws Exception {
        List<Food> listFoods = null;
        try {
            String sql = "select foodId, foodName, imageUrl, categoryId, foodPrice from Food where foodId in "
                    + "(select TOP 6 orderTable.foodId  from (select foodId , "
                    + "COUNT(foodId) over(Partition by foodId) as foodIdTable from "
                    + "OrderDetail where orderId in (select orderId from OrderDetail "
                    + "where foodId = ?)) as orderTable where "
                    + "orderTable.foodId != ? and orderTable.foodIdTable > 1 and foodQuantity > 0 "
                    + "group by orderTable.foodId order by count(orderTable.foodIdTable) desc) and status = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, food.getFoodId());
                preStm.setString(2, food.getFoodId());
                preStm.setInt(3, 1);
                resultSet = preStm.executeQuery();
                listFoods = new ArrayList<>();
                while (resultSet.next()) {
                    String foodId = resultSet.getString("foodId");
                    String foodName = resultSet.getString("foodName");
                    String imageUrl = resultSet.getString("imageUrl");
                    String categoryId = resultSet.getString("categoryId");
                    float foodPrice = resultSet.getFloat("foodPrice");
                    Food newFood = new Food();
                    newFood.setFoodId(foodId);
                    newFood.setFoodName(foodName);
                    newFood.setImageUrl(imageUrl);
                    newFood.setFoodPrice(foodPrice);
                    newFood.setCategoryId(categoryId);
                    listFoods.add(newFood);
                }
            }
        } finally {
            closeConnection();
        }
        return listFoods;
    }
}
