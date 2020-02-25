import javax.mail.*;
import java.io.*;
import java.sql.*;


public class OracleService {
    static Connection connection;
    static boolean isConnected = false;

    public static void connectToOracle(OracleProperties props){
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

    public static void write(Message[] messages, OracleProperties props) throws MessagingException, IOException, SQLException {
        System.out.print("Writing to database... ");

        for (int i = 0; i < messages.length; i++) {
            NewMessage newMessage = new NewMessage(props, messages[i]);
            String insertTableSQL = "INSERT INTO MAIL"
                    + "(TO_EMAIL, FROM_EMAIL, SUBJECT, MAIL_DATE, TEXT_FILE_DIRECTORY, ATTACHMENT_FILE_DIRECTORY) "
                    + "VALUES(?, ?, ?, ?, ?, ?)";
            try(PreparedStatement statement = connection.prepareStatement(insertTableSQL)) {
                statement.setString(1, newMessage.getTo_email());
                statement.setString(2, newMessage.getFrom_email());
                statement.setString(3, newMessage.getSubject());
                statement.setString(4, newMessage.getMail_date());
                statement.setString(5, newMessage.getText_field_directory());
                statement.setString(6, newMessage.getAttachment_field_directory());
                statement.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Successful!");
        System.out.println();
    }

    public static void closeOracleConnection() throws SQLException {
        connection.close();
    }
}
