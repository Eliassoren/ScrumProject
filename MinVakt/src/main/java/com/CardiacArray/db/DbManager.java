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
    private String dbDriver = "org.h2.Driver";

    public DbManager() throws Exception{
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("g_scrum01");
        dataSource.setDatabaseName("g_scrum01");
        dataSource.setPassword("");
        dataSource.setServerName("mysql.stud.iie.ntnu.no");
        Connection connection = dataSource.getConnection();

        try{
            connection.setAutoCommit(false);
            }

        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace(System.err);
        } finally {
            connection.close();
        }

    }

    public static void main(String[] args)throws Exception{
        DbManager db = new DbManager();
    }


}
