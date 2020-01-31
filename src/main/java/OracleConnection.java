import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {
    static UserProperties props;
    static Connection connection;
    static boolean isConnected;

    public OracleConnection(UserProperties props) throws IOException {
        this.props = props;
        isConnected = false;
        connectToOracle();
    }

    private static void connectToOracle(){
        try {
            String url = "jdbc:oracle:thin:@" + props.db_host + ":" + props.db_port + ":" + props.db_sid;
            System.out.println("Connecting to " + url + "...");
            Class.forName(props.driverName);
            connection = DriverManager.getConnection(url, props.db_login, props.db_pass);
            if (connection.equals(null))
                isConnected = false;
            else
                isConnected = true;
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            isConnected = false;
        } catch (SQLException e) {
            System.out.println("SQLException\n" + e.getMessage());
            isConnected = false;
        }
        if (isConnected) System.out.println("Connected!");
        else System.out.println("Failed!");
        System.out.println();
    }

    public void closeOracleConnection() throws SQLException {
        connection.close();
    }
}
