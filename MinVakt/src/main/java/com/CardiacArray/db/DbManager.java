package com.CardiacArray.db;
import com.CardiacArray.db.ReadConfig;
import com.CardiacArray.db.SessionDb;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by kjosavik on 10-Jan-17.
 */
public class DbManager {

    public static Connection connection;

    public DbManager() throws Exception{
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
                connection.setAutoCommit(false);
            }catch (SQLException e){
                connection.rollback();
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
        SessionDb sessionTest = new SessionDb();
        System.out.println(sessionTest.login("epost@internett.no", "123"));
        System.out.println(sessionTest.login("eposten@internett.no", "123"));
        System.out.println(sessionTest.login("oddErik@gmail.com", "123"));
        db.connection.close();
    }


}
