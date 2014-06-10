package dms.control;


import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import dms.view.EditDocumentWindow;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUploader implements Receiver, Upload.FinishedListener, Upload.StartedListener {

    private EditDocumentWindow windwo;
    private int version;

    public FileUploader(EditDocumentWindow windwo, int version) {
        this.windwo = windwo;
        this.version = version;
    }

    /**
     * return an OutputStream that simply counts lineends
     * @param MIMEType
     */
    @Override
    public OutputStream receiveUpload(final String filename, final String MIMEType) {
        String[] parse = filename.split("\\.");
        String fileEnding = parse[parse.length-1];
        String newFilename = parse[0];
        TextField name = windwo.getName();
        if(name.getValue() == null || name.getValue().isEmpty()) name.setValue(newFilename);
        else newFilename = name.getValue().toLowerCase();
        name.setEnabled(false);
        
            // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        File file = null;
        String finalFilename = "uploads" + File.separator + newFilename + version + "." + fileEnding;
        windwo.setPath(finalFilename);
        windwo.setDocumentType(fileEnding);
        
        try {
            // Open the file for writing.
            file = new File(finalFilename);
            file.getParentFile().mkdirs();
            file.createNewFile();
            fos = new FileOutputStream(file);
        } catch (final FileNotFoundException ex) {
            new Notification("Could not open file",
                             ex.getMessage(),
                             Notification.Type.ERROR_MESSAGE)
                .show(Page.getCurrent());
            return null;
        } catch (IOException ex) {
        }
        return fos; // Return the output stream to write to
    }

    @Override
    public void uploadFinished(Upload.FinishedEvent event) {
    }

    @Override
    public void uploadStarted(Upload.StartedEvent event) {
    }
}