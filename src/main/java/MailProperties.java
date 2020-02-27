import java.io.IOException;
import java.util.Properties;

public class MailProperties {
    private static int counterMail = 0;
    private String login;
    private String password;
    private String host;
    private int port;
    private String mailRemover;
    public static boolean isNextMail = true;

    public MailProperties() {
        nextMail();
    }

    public int getCounterMail() {
        return counterMail;
    }

    public void nextMail(){
        try {
            counterMail++;
            Properties props = GetProperties.getProperties();
            this.host = props.getProperty("mail.pop3.host" + counterMail);
            this.port = Integer.parseInt(props.getProperty("mail.pop3.port" + counterMail));
            this.login = props.getProperty("mail.login" + counterMail);
            this.password = props.getProperty("mail.password" + counterMail);
            this.mailRemover = props.getProperty("mail.remover" + counterMail);
        } catch (Exception e) {
            isNextMail = false;
        }
    }

    public static boolean isNextMail() throws IOException {
            String test = GetProperties.getProperties().getProperty("mail.pop3.host" + (counterMail+1));
            if(test!=null) return true;
            else return false;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getMailRemover() {
        return mailRemover;
    }
}
