import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.print.Doc;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;

import oracle.jdbc.*;

public class WriteToOracle {
    Message[] messages;
    UserProperties props;

    public WriteToOracle(UserProperties props, Message[] messages) throws IOException, MessagingException, SQLException {
        this.messages = messages;
        this.props = props;
        write();
    }

    private void write() throws MessagingException, IOException, SQLException {
        System.out.println("Writing to database...");
        System.out.println();

        for (int i = 0; i < messages.length; i++) {
            NewMessage newMessage = new NewMessage(props, messages[i]);
            String insertTableSQL = "INSERT INTO MAIL"
                    + "(TO_EMAIL, FROM_EMAIL, SUBJECT, MAIL_DATE, TEXT_FILE_DIRECTORY, ATTACHMENT_FILE_DIRECTORY) "
                    + "VALUES(?, ?, ?, ?, ?, ?)";
            try(PreparedStatement statement = OracleConnection.connection.prepareStatement(insertTableSQL)) {
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


}
