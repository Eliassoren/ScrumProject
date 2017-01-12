package com.CardiacArray.db;

import com.CardiacArray.data.User;

import java.sql.*;

/**
 * Created by kjosavik on 11-Jan-17.
 */
public class UserDb {
    private Connection connection;
    private ResultSet res;
    private PreparedStatement statement;

    public UserDb(Connection connection){
        this.connection = connection;
    }


    public User getUser(String email){
        try {
            String toSQL = "select * from user where email = ?";
            statement = connection.prepareStatement(toSQL);
            statement.setString(1, email);
            res = statement.executeQuery()
            if(res.next()){
                int id = res.getInt("user_id");
                String firstName= res.getString("fist_name");
                String lastName = res.getString("last_name");
                String password = res.getString("password");
                int adminRights = res.getInt("admin_rights");
                int mobile = res.getInt("mobile");
                String address = res.getString("address");
            User user = new User(id,firstName,lastName,mobile,email,password)


        }

    }
   
}
