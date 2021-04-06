/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DELL
 */
public class CategoryDAO {

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

    public CategoryDAO() {
    }

    public List<Category> getAllCategory() throws Exception {
        List<Category> categoryList = null;
        String sql = "Select categoryId, categoryName from Category";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                categoryList = new ArrayList<>();
                while (resultSet.next()) {
                    String categoryId = resultSet.getString("categoryId");
                    String categoryName = resultSet.getString("categoryName");
                    Category category = new Category(categoryId, categoryName);
                    categoryList.add(category);
                }
            }
        } finally {
            closeConnection();
        }
        return categoryList;
    }
}
