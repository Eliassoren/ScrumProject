package com.CardiacArray.db;

import com.CardiacArray.data.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by kjosavik on 11-Jan-17.
 */
public class UserDb extends DbManager {
    private ResultSet res;
    private PreparedStatement statement;

    /**
    @param id Id of user
    @return User specified by id
     */
    public User getUserByEmail(int id){
        User user = new User();
        try {
            String toSQL = "select * from user join user_category " +
                    "on user.user_category_id = user_category.user_category_id " +
                    "where user_id=?";
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, id);
            res = statement.executeQuery();
            if(res.next()){
                String email = res.getString("email");
                String firstName= res.getString("first_name");
                String lastName = res.getString("last_name");
                String password = res.getString("password");
                boolean adminRights = res.getBoolean("admin_rights");
                int mobile = res.getInt("mobile");
                String address = res.getString("address");
                int userCategoryInt = res.getInt("user.user_category_id");
                String userCategoryString = res.getString("type");
                String token = res.getString("token");
                Timestamp expired = res.getTimestamp("expired");
                boolean active = res.getBoolean("active");
                user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active);
                res.close();
                statement.close();
            } else{ return null; }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
            DbManager.rollback();
        }
        return user;
    }

    /**
    @param email
    @return User specified by email
     */
    public User getUserByEmail(String email){
        User user = new User();
        try {
            String toSQL = "select * from user join user_category " +
                    "on user.user_category_id = user_category.user_category_id " +
                    "where email=?";
            statement = connection.prepareStatement(toSQL);
            statement.setString(1, email);
            res = statement.executeQuery();
            if(res.next()){
                int id = res.getInt("user_id");
                String firstName= res.getString("first_name");
                String lastName = res.getString("last_name");
                String password = res.getString("password");
                boolean adminRights = res.getBoolean("admin_rights");
                int mobile = res.getInt("mobile");
                String address = res.getString("address");
                int userCategoryInt = res.getInt("user.user_category_id");
                String userCategoryString = res.getString("type");
                String token = res.getString("token");
                Timestamp expired = res.getTimestamp("expired");
                boolean active = res.getBoolean("active");
                user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active);
                res.close();
                statement.close();
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
            DbManager.rollback();
        }
        return user;
    }


    /**
     @param token
     @return User
     */
    public User getUserByToken(String token){
        try {
            String toSQL = "select * from user join user_category " +
                    "on user.user_category_id = user_category.user_category_id " +
                    "where token=?";
            PreparedStatement statement = connection.prepareStatement(toSQL);
            statement.setString(1, token);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                int id = res.getInt("user_id");
                String firstName= res.getString("first_name");
                String lastName = res.getString("last_name");
                String email = res.getString("email");
                String password = res.getString("password");
                boolean adminRights = res.getBoolean("admin_rights");
                int mobile = res.getInt("mobile");
                String address = res.getString("address");
                int userCategoryInt = res.getInt("user.user_category_id");
                String userCategoryString = res.getString("type");
                Timestamp expired = res.getTimestamp("expired");
                boolean active = res.getBoolean("active");
                User user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active);
                res.close();
                statement.close();
                return user;
            } else{
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
            DbManager.rollback();
            return null;
        }
    }

    /**
    @param user
    @return boolean
     */
    public boolean updateUser(User user){
        try {
            String toSQL = "UPDATE user " +
                    "SET first_name = ?, last_name = ?, password = ?, admin_rights = ?, mobile = ?, address = ?, user_category_id = ?, email = ?, active = ? " +
                    "WHERE user_id = ?";
            statement = connection.prepareStatement(toSQL);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isAdmin());
            statement.setInt(5, user.getMobile());
            statement.setString(6, user.getAddress());
            statement.setInt(7, user.getUserCategoryInt());
            statement.setString(8, user.getEmail());
            statement.setBoolean(9, user.isActive());
            statement.setInt(10,user.getId());
            statement.execute();
            statement.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace(System.err);
            DbManager.rollback();
            return false;
        }
    }

    public boolean updateUserToken(User user) {
        try {
            String toSQL = "UPDATE user SET token = ?, expired = ? WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(toSQL);
            statement.setString(1, user.getToken());
            statement.setTimestamp(2, user.getExpired());
            statement.setInt(3, user.getId());
            statement.execute();
            statement.close();
            return true;

        } catch(SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }
    }

    /**
     *@deprecated
     *@param user
     */
    public void deleteUser(User user){
        try {
            String toSQL = "DELETE FROM user WHERE user_id =?";
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1,user.getId());
            statement.execute();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace(System.err);
            DbManager.rollback();
        }
    }

    /**
    * @param user
    * @return User ID generated by the database, else -1 if an error occurs.
     */
    public int createUser(User user){
        int returnValue = -1;
        try {
            String toSQL = "INSERT into user (user_id, first_name, last_name, password, admin_rights, user_category_id, mobile, address, email, active)\n" +
                    "  VALUES (DEFAULT ,?, ?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(toSQL);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isAdmin());
            statement.setInt(6, user.getMobile());
            statement.setString(7, user.getAddress());
            statement.setInt(5, user.getUserCategoryInt());
            statement.setString(8, user.getEmail());
            statement.setBoolean(9, user.isActive());
            statement.execute();
            ResultSet res = statement.getGeneratedKeys();
            if(res.next()){
                returnValue = res.getInt(1);
            } else{
                return returnValue;
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace(System.err);
            DbManager.rollback();
        }
        return returnValue;
    }

    public boolean setUserAvailable(int userId, Date date, Date start, Date end) {
        SimpleDateFormat simpleDateAndTime = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        String dateFormat = simpleDateAndTime.format(date);
        String startFormat = simpleTime.format(start.getTime());
        String endFormat = simpleTime.format(end.getTime());
        try {
            String toSQL = "INSERT INTO availability (user_id, availability.date, availability.start, availability.end) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, userId);
            statement.setString(2, dateFormat);
            statement.setString(3, startFormat);
            statement.setString(4, endFormat);
            statement.execute();
            statement.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            DbManager.rollback();

            return false;
        }
    }

    public ArrayList<User> getAvailableUsers(long startLong, long endLong){
        ArrayList<User> users = new ArrayList<User>();
        try {
            String toSQL = "SELECT * FROM user JOIN availability ON user.user_id = availability.user_id JOIN user_category ON user.user_category_id = user_category.user_category_id" +
                    " WHERE start_time BETWEEN ?" +
                    " AND ? OR end_time BETWEEN ?" +
                    " AND ?";
            Timestamp startStamp = Timestamp.from(Instant.ofEpochMilli(startLong));
            Timestamp endStamp = Timestamp.from(Instant.ofEpochMilli(endLong));
            statement = connection.prepareStatement(toSQL);
            statement.setTimestamp(1, startStamp);
            statement.setTimestamp(2, endStamp);
            statement.setTimestamp(3, startStamp);
            statement.setTimestamp(4, endStamp);
            res = statement.executeQuery();
            while(res.next()) {
                int id = res.getInt("id");
                String email = res.getString("email");
                String firstName= res.getString("first_name");
                String lastName = res.getString("last_name");
                String password = res.getString("password");
                boolean adminRights = res.getBoolean("admin_rights");
                int mobile = res.getInt("mobile");
                String address = res.getString("address");
                int userCategoryInt = res.getInt("user.user_category_id");
                String userCategoryString = res.getString("type");
                String token = res.getString("token");
                Timestamp expired = res.getTimestamp("expired");
                boolean active = res.getBoolean("active");
                Timestamp startTime = res.getTimestamp("start_time");
                Timestamp endTime = res.getTimestamp("end_time");
                User user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active);
                users.add(user);
            }
            res.close();
            statement.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Main methode that does the following:
     * creates a connection with DbManager
     * creates a user (testUser1)
     * retrieves same user from database(testUser2)
     * updates testUser in database
     * retrieves updated user to check if update is successful
     * and finally deletes user from database
    */
    public static void main(String[] args)throws Exception{
        User testUser1 = new User("Dirck", "Delete", 90269026, "dirk@delete.com", "passs", false, "trondheim", 0,true);
        UserDb udb = new UserDb();
        udb.createUser(testUser1);
        User testUser2 = udb.getUserByEmail(testUser1.getEmail());
        int testCounter = 0;
        if(testUser1.getEmail().equals(testUser2.getEmail())) {
            System.out.println("user added and retrieved OK");
            testCounter ++;
        }else{
            System.out.println("user added and retrievde FAILED");
        }
        testUser2.setFirstName("David");
        udb.updateUser(testUser2);
        User testUser3 = udb.getUserByEmail(testUser2.getId());
        if(testUser2.getFirstName().equals(testUser3.getFirstName())){
            System.out.println("user update OK");
            testCounter ++;
        }else{
            System.out.println("user update FAILED");
        }
        udb.deleteUser(testUser2);
        System.out.println(testCounter + "/2 tests complete.");
    }
}
