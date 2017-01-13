package com.CardiacArray.db;


import java.sql.*;

/**
 * Created by kjosavik on 11-Jan-17.
 */
public class SessionDb extends DbManager {
    private ResultSet res;
    private PreparedStatement statement;

    public SessionDb(Connection connection){
       this.connection = connection;
    }

    public int login(String email, String password) {
        int adminRights = -1;
        try {
            String toSQL = "select admin_rights from user where email = ? and password = ?";
            statement = connection.prepareStatement(toSQL);
            statement.setString(1, email);
            statement.setString(2, password);
            res = statement.executeQuery();
            if (!res.next()) {
                return adminRights;
            } else {
                adminRights = res.getInt(1);
            }
            res.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return adminRights;
    }

}
