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
    //private String dbLink = "jdbc:h2://mysql.stud.iie.ntnu.no/g_scrum01?user=g_scrum01&password=DarP7yTs";


    public DbManager() throws Exception{
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("g_scrum01");
        dataSource.setDatabaseName("g_scrum01");
        dataSource.setPassword("");
        dataSource.setServerName("mysql.stud.iie.ntnu.no");
        Connection connection = dataSource.getConnection();
        Statement statement = null;
        ResultSet res = null;
        ArrayList<String> al = new ArrayList<>();

        String toSQL = "select * from user;";
        try{
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            res = statement.executeQuery(toSQL);
            connection.commit();

            int tuppleIndex = 1;
            while(res.next()){
                System.out.println(res.getString(tuppleIndex));
                tuppleIndex ++;
            }

        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace(System.err);
        } finally {
            res.close();
            statement.close();
            connection.close();
        }

    }

    public static void establishConnection(Connection newConnection){
        connection = newConnection;
    }

    public static void main(String[] args)throws Exception{
        DbManager db = new DbManager();
    }


}
