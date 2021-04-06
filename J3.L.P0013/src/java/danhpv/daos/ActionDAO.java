/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Action;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author DELL
 */
public class ActionDAO {

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

    public ActionDAO() {
    }

    public String getActionIdWithLastAction() throws Exception {
        String actionId = null;
        try {
            String sql = "select actionId from Action where updateDate = (select max(updateDate) from Action)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    actionId = resultSet.getString("actionId");
                }
            }
        } finally {
            closeConnection();
        }
        return actionId;
    }

    public boolean addNewAction(Action action) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "insert into Action values(?, ?, ?, ?, ?, ?)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, action.getActionId());
                preStm.setString(2, action.getFoodId());
                preStm.setString(3, action.getUpdateUser());
                preStm.setString(4, action.getTypeAction());
                preStm.setTimestamp(5, new Timestamp(action.getUpdateDate().getTime()));
                preStm.setBoolean(6, action.isStatus());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }
    
}
