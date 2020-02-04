import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.*;

public class FileSaver {

    public static String toSaveAttachment(BodyPart bodyPart) throws IOException, MessagingException {
        return saveAttachment(bodyPart);
    }

    public static String toSaveText(Object content) throws IOException, MessagingException {
        return saveText((String) content);
    }

    private static String saveAttachment(BodyPart part) throws IOException, MessagingException {
        InputStream is = part.getInputStream();
        String attachmentDir = GetProperties.getProperties().getProperty("dir.attachments") + File.separator;
        attachmentDir = generatorNames(attachmentDir, "attachment") + "_" + MimeUtility.decodeText(part.getFileName());
        File f = new File(attachmentDir);
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buf = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(buf)) != -1) {
            fos.write(buf, 0, bytesRead);
        }
        fos.close();
        return attachmentDir;
    }

    private static String saveText(String part) throws IOException, MessagingException {
        String textDir;
        String text;
        Document doc = Jsoup.parse(part);
        text = doc.text();
        textDir = GetProperties.getProperties().getProperty("dir.messages") + File.separator;
        textDir = generatorNames(textDir, "text");
        try {
            FileWriter writer = new FileWriter(textDir, false);
            writer.write(String.valueOf(text));
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return textDir;
    }

    private static String generatorNames(String fieldDir, String fileType) {
        String dir;
        File listFiles = new File(fieldDir);
        if (listFiles.listFiles().length == 0)
            dir = fieldDir + fileType + "1";
        else
            dir = fieldDir + fileType + (listFiles.listFiles().length + 1);
        return dir;
    }

}
