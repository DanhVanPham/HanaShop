/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author DELL
 */
public class RoleDAO {

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

    public RoleDAO() {
    }

    public Role getRoleByRoleId(String roleId) throws Exception {
        Role role = null;
        try {
            String sql = "Select roleName from Role where roleId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, roleId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String roleName = resultSet.getString("roleName");
                    role = new Role(roleId, roleName);
                }
            }
        } finally {
            closeConnection();
        }
        return role;
    }
}
