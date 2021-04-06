/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author DELL
 */
public class UserDAO {
    
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
    
    public UserDAO() {
    }
    
    public User checkLogin(String userId, String pass) throws Exception {
        User userDTO = null;
        try {
            String sql = "Select roleId, fullName, phoneNumber ,address, status from Users where userId = ? and password = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, userId);
                preStm.setString(2, pass);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String roleId = resultSet.getString("roleId");
                    String fullName = resultSet.getString("fullName");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String address = resultSet.getString("Address");
                    int status = resultSet.getInt("status");
                    userDTO = new User();
                    userDTO.setUserId(userId);
                    userDTO.setFullName(fullName);
                    userDTO.setRoleId(roleId);
                    userDTO.setPhoneNumber(phoneNumber);
                    userDTO.setAddress(address);
                    userDTO.setStatus(status);
                }
            }
        } finally {
            closeConnection();
        }
        return userDTO;
    }
    
    public User checkLoginWithGoogleAccount(String userId) throws Exception {
        User userDTO = null;
        try {
            String sql = "Select roleId, fullName, phoneNumber ,address, status from Users where userId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, userId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String roleId = resultSet.getString("roleId");
                    String fullName = resultSet.getString("fullName");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String address = resultSet.getString("Address");
                    int status = resultSet.getInt("status");
                    userDTO = new User();
                    userDTO.setUserId(userId);
                    userDTO.setFullName(fullName);
                    userDTO.setRoleId(roleId);
                    userDTO.setPhoneNumber(phoneNumber);
                    userDTO.setAddress(address);
                    userDTO.setStatus(status);
                }
            }
        } finally {
            closeConnection();
        }
        return userDTO;
    }
}
