package com.CardiacArray.restService.db;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.sql.SQLException;
import java.util.TimeZone;


/**
 * Created by kjosavik on 10-Jan-17.
 */
public class DbManager {

    public static Connection connection;

    /**
     * Constructor that establishes a connection with that database.
     * All other database classes inherit this connection.
     */
    public DbManager() {
        try {
            if(connection == null || connection.isClosed()) {
                TimeZone.setDefault(TimeZone.getTimeZone("Europe/Oslo"));
                ReadConfig readConfig = new ReadConfig();
                String[] result = readConfig.getConfigValues();
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setUser(result[0]);
                dataSource.setDatabaseName(result[0]);
                dataSource.setPassword(result[1]);
                dataSource.setServerName("mysql.stud.iie.ntnu.no");
                connection = dataSource.getConnection();
                connection.setAutoCommit(true);
            }
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }

    /**
     *
     */
    public static void rollback(){
        try{
            connection.rollback();
        }catch(SQLException e){
            e.printStackTrace(System.err);
        }
    }

    public static void main(String[] args)throws Exception{
        DbManager db = new DbManager();
        db.connection.close();
    }


}
