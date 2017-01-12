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
        User user = new User();
        try {
            String toSQL = "select * from user join user_category" +
                    "on user.user_category_id = user_category.user_category_id" +
                    "where email=? ";
            statement = connection.prepareStatement(toSQL);
            statement.setString(1, email);
            res = statement.executeQuery();
            if(res.next()){
                int id = res.getInt("user_id");
                String firstName= res.getString("fist_name");
                String lastName = res.getString("last_name");
                String password = res.getString("password");
                int adminRights = res.getInt("admin_rights");
                int mobile = res.getInt("mobile");
                String address = res.getString("address");
                int userCategoryInt = res.getInt("user.user_category_id");
                String userCategoryString = res.getString("type");
                user = new User(id,firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt,userCategoryString);
                res.close();
                statement.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
            DbManager.rollback();
        }
        return user;
    }

    public void updateUser(User user){
        try {
            String toSQL = "UPDATE user" +
                    "SET first_name=?, last_name=?, password=?, admin_rights=?, mobile=?, address=?, user_category_id=?, email=?" +
                    "WHERE user_id = ?";
            statement = connection.prepareStatement(toSQL);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getAdmin());
            statement.setInt(5, user.getMobile());
            statement.setString(6, user.getAddress());
            statement.setInt(7, user.getUserCategoryInt());
            statement.setString(8, user.getEmail());
            statement.setInt(user.getId());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace(System.err);
            DbManager.rollback();
        }

    }
}
