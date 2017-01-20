package com.CardiacArray.db;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.sql.SQLException;


/**
 * Created by kjosavik on 10-Jan-17.
 */
public class DbManager {

    public static Connection connection;

    public DbManager() {
        if(connection == null){
            try {
                ReadConfig readConfig = new ReadConfig();
                String [] result = readConfig.getConfigValues();
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setUser(result[0]);
                dataSource.setDatabaseName(result[0]);
                dataSource.setPassword(result[1]);
                dataSource.setServerName("mysql.stud.iie.ntnu.no");
                connection = dataSource.getConnection();
                connection.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
    }

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
