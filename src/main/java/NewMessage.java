import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class NewMessage {
    OracleProperties props;
    Message message;
    //Переменные полученного сообщения
    private String to_email;
    private String from_email;
    private String subject;
    private Date mail_date;
    private String text_field_directory;
    private String attachment_field_directory;

    public void setFrom_email(Address[] from_email) {
        this.from_email = from_email == null ? null : ((InternetAddress) from_email[0]).getAddress();
    }

    public String getTo_email() {
        return to_email;
    }

    public String getFrom_email() {
        return from_email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMail_date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(mail_date);
    }

    public String getText_field_directory() {
        return text_field_directory;
    }

    public String getAttachment_field_directory() {
        return attachment_field_directory;
    }

    public NewMessage(OracleProperties props, Message message) throws IOException, MessagingException {
        this.props = props;
        this.message = message;
        setParametersForNewMessage();
        message.setFlag(Flags.Flag.DELETED, true);
    }

    private void setParametersForNewMessage() throws MessagingException, IOException {
        MimeMessage mimeMessage = (MimeMessage) message;
        to_email = Arrays.toString(mimeMessage.getAllRecipients());
        setFrom_email(mimeMessage.getFrom());
        subject = MimeUtility.decodeText(mimeMessage.getSubject());
        mail_date = mimeMessage.getSentDate();

        if (mimeMessage.isMimeType("multipart/mixed") || mimeMessage.isMimeType("multipart/alternative")) {
            Multipart multipart = (Multipart) mimeMessage.getContent();
            multipart_func(multipart);
        } else if (mimeMessage.isMimeType("text/html") || mimeMessage.isMimeType("text/plain")) {
            text_field_directory = FileSaver.toSaveText(mimeMessage.getContent());
        }
    }

    //Функция для разбора Multipart
    private void multipart_func(Multipart multiPart) throws MessagingException, IOException {
        text_field_directory = "";
        for (int i = 0; i < multiPart.getCount(); i++) {
            BodyPart bodyPart = multiPart.getBodyPart(i);
            if (bodyPart.isMimeType("multipart/alternative")) {
                Multipart multiPart_alternative = (Multipart) bodyPart.getContent();
                multipart_func(multiPart_alternative);
            }
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                attachment_field_directory = FileSaver.toSaveAttachment(bodyPart);
                continue;
            }
            if (bodyPart.isMimeType("text/html")) {
                if (!bodyPart.getContent().equals("") && !bodyPart.getContent().equals("<![endif]-->"))
                    text_field_directory = FileSaver.toSaveText(bodyPart.getContent());
            }
        }
    }

}
