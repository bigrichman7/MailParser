import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class MailService {
    static Store store;
    static Folder inbox;
    static Message[] messages;

    public static void connectToMail(UserProperties props) throws IOException {
        Session session = null;
        session = Session.getDefaultInstance(UserProperties.getProperties(), null);
        try {
            assert session != null;
            store = session.getStore("pop3s");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        System.out.print("Connecting to " + props.host + "...");
        try {
            store.connect(props.host, props.port, props.login, props.password);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Connected!");
        try {
            openINBOX();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        System.out.println("Finded " + messages.length + " new messages");
        System.out.println();
    }

    private static void openINBOX() throws MessagingException {
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        messages = inbox.getMessages();
    }

    public static void closeMailConnection() throws MessagingException {
        inbox.close(false);
        store.close();
        System.exit(0);
    }
}
