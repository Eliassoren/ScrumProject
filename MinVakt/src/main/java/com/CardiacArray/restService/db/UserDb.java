package com.CardiacArray.restService.db;

import com.CardiacArray.restService.data.Available;
import com.CardiacArray.restService.data.User;
import com.CardiacArray.restService.data.Department;
import com.CardiacArray.restService.data.UserCategory;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;


/**
 * Created by kjosavik on 11-Jan-17.
 */
public class UserDb extends DbManager {
    private ResultSet res;
    private PreparedStatement statement;

    /*
    * @param id Id of user
    * @return User specified by id
     */
    public User getUserById(int id){
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
                int workPercent = res.getInt("work_percent");
                int departmentId = res.getInt("department_id");
                user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active, workPercent, departmentId);
                res.close();
                statement.close();
            } else{ return null; }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
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
                int workPercent = res.getInt("work_percent");
                int departmentId = res.getInt("department_id");
                user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active, workPercent, departmentId);
                res.close();
                statement.close();
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return user;
    }


    /**
     *This method is used to get the user which is logged on.
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
                int workPercent = res.getInt("work_percent");
                int departmentId = res.getInt("department_id");
                User user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active, workPercent, departmentId);
                res.close();
                statement.close();
                return user;
            } else{
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     *
     * @param departmentId Id of the department which you would like to list out all users.
     * @return All users that are in a department.
     */
    public ArrayList<User> getUsersByDepartmentId(int departmentId) {
        ArrayList<User> users = new ArrayList<>();
        try {
            String toSQL = "select * from user join user_category " +
                    "on user.user_category_id = user_category.user_category_id " +
                    "where department_id=?";
            PreparedStatement statement = connection.prepareStatement(toSQL);
            statement.setInt(1, departmentId);
            ResultSet res = statement.executeQuery();
            while(res.next()){
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
                String token = res.getString("token");
                Timestamp expired = res.getTimestamp("expired");
                boolean active = res.getBoolean("active");
                int workPercent = res.getInt("work_percent");
                User user = new User(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active, workPercent, departmentId);
                users.add(user);
            }
            res.close();
            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
    @param user
    @return True if the update was successful.
     */
    public boolean updateUser(User user){
        try {
            String toSQL = "UPDATE user " +
                    "SET first_name = ?, last_name = ?, password = ?, admin_rights = ?, mobile = ?, address = ?, user_category_id = ?, email = ?, active = ?, work_percent = ?, department_id = ? " +
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
            statement.setInt(10,user.getWorkPercent());
            statement.setInt(11, user.getDepartmentId());
            statement.setInt(12,user.getId());
            statement.execute();
            statement.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace(System.err);
            return false;
        }
    }

    /**
     * Used when a user logs on at a different location.
     * @param user
     * @return True if the tolken update was successful.
     */
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
     * If a user works at the facility he is considered active.
     * @param user
     * @return True, if the update was successfull.
     */
    public boolean setUserActive(User user){
        try {
            String toSQL = "UPDATE user SET active = ? where user_id = ?";
            PreparedStatement statement = connection.prepareStatement(toSQL);
            statement.setBoolean(1, user.isActive());
            statement.setInt(2,user.getId());
            statement.execute();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }


    }

    /**
     * Deletes a user from the database entirely. Only used to delete test users.
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
        }
    }

    /**
     *
    * @param user
    * @return User ID generated by the database, else -1 if an error occurs.
     */
    public int createUser(User user){
        int returnValue = -1;
        try {
            String toSQL = "INSERT into user (first_name, last_name, password, admin_rights, user_category_id, mobile, address, email, active, work_percent, department_id)\n" +
                    "  VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setInt(10, user.getWorkPercent());
            statement.setInt(11, user.getDepartmentId());
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
        }
        return returnValue;
    }

    /** Sets a user as available for given dates
     *
     * @param userId id of the user
     * @param start start date
     * @param end end date
     * @return true if user set available
     */
    public boolean setUserAvailable(int userId, long start, long end) {
        try {
            String toSQL = "INSERT INTO availability (availability.user_id, availability.start_time, availability.end_time) VALUES (?,?,?)";
            Timestamp startStamp = Timestamp.from(Instant.ofEpochMilli(start));
            Timestamp endStamp = Timestamp.from(Instant.ofEpochMilli(end));
            statement = connection.prepareStatement(toSQL);
            statement.setInt(1, userId);
            statement.setTimestamp(2, startStamp);
            statement.setTimestamp(3, endStamp);
            statement.execute();
            statement.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            return false;
        }
    }
    /**
     * Users that have registered themselves as available will be fetched by this method.
     * @param startLong start date
     * @param endLong end date
     * @return list of available users
     */
    public ArrayList<Available> getAvailableUsers(long startLong, long endLong){
        ArrayList<Available> availables = new ArrayList<Available>();
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
                int id = res.getInt("user_id");
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
                int workpercent = res.getInt("work_percent");
                int departmentId = res.getInt("department_id");
                Available tempAvailable = new Available(id, firstName,lastName,mobile,email,password,adminRights,address,userCategoryInt, userCategoryString, token, expired, active, workpercent,departmentId,new Date(startLong),new Date(endLong));
                availables.add(tempAvailable);
            }
            res.close();
            statement.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return availables;
    }

    /**
     *
     * @param id id of the user
     * @param date date to check
     * @return true if user has a shift that date
     */
    public boolean userHasShift(int id, java.sql.Date date) {
        try {
            String toSQL = "select * from shift " +
            " join user_shift on shift.shift_id = user_shift.shift_id" +
            " join user on user_shift.user_id = user.user_id" +
            " where shift.date = ? and user.user_id = ?";
            statement = connection.prepareStatement(toSQL);
            statement.setDate(1, date);
            statement.setInt(2, id);
            res = statement.executeQuery();
            if (!res.next()) {
                return false;
            }
            res.close();
            statement.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<User> getAllUsers(){
        ArrayList<User> al = new ArrayList<>();
        try {
            String toSQL = "select * from user join user_category " +
                    "on user.user_category_id = user_category.user_category_id WHERE active = 1";
            statement = connection.prepareStatement(toSQL);
            res = statement.executeQuery();
            while(res.next()) {
                int id = res.getInt("user_id");
                String email = res.getString("email");
                String firstName = res.getString("first_name");
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
                int workPercent = res.getInt("work_percent");
                int departmentId = res.getInt("department_id");
                User user = new User(id, firstName, lastName, mobile, email, password, adminRights, address, userCategoryInt, userCategoryString, token, expired, active, workPercent, departmentId);
                al.add(user);
            }
                res.close();
                statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return al;
    }
    /**
     *
     * @return ArrayList of all departments.
     */
    public ArrayList<Department> getDepartments() {
         ArrayList<Department> departments = new ArrayList<>();
         try {
             String toSQL = "SELECT * FROM department ORDER BY department_id ASC";
             statement = connection.prepareStatement(toSQL);
             res = statement.executeQuery();
             while (res.next()) {
                 int departmentId = res.getInt("department_id");
                 String departmentName = res.getString("department_name");
                 int departmentPhone = res.getInt("phone");
                 Department department = new Department(departmentId, departmentName, departmentPhone);
                 departments.add(department);
             }
             statement.close();
             res.close();
            }catch (SQLException e) {
             e.printStackTrace(System.err);
         }
         return departments;
    }

    /**
     *
     * @return ArrayList of all user categories.
     */
    public ArrayList<UserCategory> getUserCategories() {
        ArrayList<UserCategory> userCategories = new ArrayList<>();
        try {
            String toSQL = "SELECT * FROM user_category ORDER BY user_category_id ASC";
            statement = connection.prepareStatement(toSQL);
            res = statement.executeQuery();
            while (res.next()) {
                int userCategoryId = res.getInt("user_category_id");
                String userCategoryType = res.getString("type");
                UserCategory userCategory = new UserCategory(userCategoryId, userCategoryType);
                userCategories.add(userCategory);
            }
            statement.close();
            res.close();
        }catch (SQLException e) {
            e.printStackTrace(System.err);
        }
        return userCategories;
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
        User testUser1 = new User("Dirck", "Delete", 90269026, "dirk@delete.com", "passs", false, "trondheim", 0,true, 60);
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
        User testUser3 = udb.getUserById(testUser2.getId());
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
