import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kjosavik on 10-Jan-17.
 */
public class DbManager {

    public static Connection connection;
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbLink = "jdbc:mysql://mysql.stud.iie.ntnu.no/g_scrum01?user=g_scrum01&password=DarP7yTs";

    public DbManager() throws Exception{
        Class.forName(dbDriver);
        Connection connection = DriverManager.getConnection(dbLink);
        Statement statement = null;
        ResultSet res = null;
        ArrayList<String> al = new ArrayList<>();

        String toSQL = "select * from user;";
        try{
            connection.setAutoCommit(true);
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
        }

    }

    public static void establishConnection(Connection newConnection){
        connection = newConnection;
    }

    public static void main(String[] args)throws Exception{
        DbManager db = new DbManager();
    }


}
