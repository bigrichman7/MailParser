import javax.mail.*;
import java.io.IOException;

public class MailService {
    static Store store;
    static Folder inbox;
    public static Message[] messages;

    public static void connectToMail(MailProperties props) throws IOException, MessagingException {
        Session session = null;
        session = Session.getDefaultInstance(GetProperties.getProperties(), null);
        try {
            assert session != null;
            store = session.getStore("pop3s");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        System.out.print("Connecting to " + props.getHost() + "... ");
        try {
            store.connect(props.getHost(), props.getPort(), props.getLogin(), props.getPassword());
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
    }

    private static void openINBOX() throws MessagingException {
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);
        messages = inbox.getMessages();
    }

    public static void closeMailConnection() throws MessagingException, IOException {
        inbox.close(Boolean.parseBoolean(GetProperties.getProperties().getProperty("mail.remover" + MailProperties.getInstance().getCounterMail())));
        store.close();
    }
}
