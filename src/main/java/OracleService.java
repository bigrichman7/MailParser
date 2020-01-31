import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.print.Doc;
import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;

import oracle.jdbc.*;

public class OracleService {
    static UserProperties props;
    static Connection connection;
    static boolean isConnected;

    public OracleService(UserProperties props) throws IOException, MessagingException, SQLException {
        OracleService.props = props;
        isConnected = false;
    }

    public void connectToOracle(){
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

    public void write(Message[] messages) throws MessagingException, IOException, SQLException {
        System.out.println("Writing to database...");
        System.out.println();

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
    }

    public void closeOracleConnection() throws SQLException {
        connection.close();
    }
}
