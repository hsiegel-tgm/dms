package dms.control;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.model.Category;
import dms.model.KeyWord;
import dms.model.User;
import dms.view.EditDocumentWindow;
import java.util.Set;


/**
 * Listenes on the create of an event
 * @author Dominik Scholz
 * @version 0.1
 */
public class CreateDocumentListener implements Button.ClickListener {
    
    private EditDocumentWindow window;
    
    public CreateDocumentListener(EditDocumentWindow window) {
        this.window = window;
    }
    
    @Override
    public void buttonClick(Button.ClickEvent event) {
        if(!window.isUploaded()){
            window.displayErrorMessage("S'il vous plait enregistrer une dossier!");
            return;
        }
        
        String name = window.getName().getValue();
        if(name == null || name.isEmpty() || name.trim().isEmpty()) {
            window.displayErrorMessage("le document n'as pas de nom!");
            return;
        }
        
        String description = window.getDocumentDescription().getValue();
        String path = window.getPath();
        String documentType = window.getDocumentType();
         
        Set<User> users = window.getUsers();
        Set<Category> categories = window.getCategories();
        Set<KeyWord> keywords = window.getKeyWords();
        
        try {
            DocumentManager dm = ((DmsUI)UI.getCurrent()).getDmsSession().getDocumentManager();

            dm.create(name, description, documentType, window.getVersion(), path, users, categories, keywords);

            event.getButton().removeClickShortcut();
            window.close();
            Notification note;
            note = window.getPrefill() ? 
                new Notification("Engreistre un nouveau version du document \"" + name + "\"", Notification.Type.TRAY_NOTIFICATION):
                            new Notification("Enregistre une nouveau document \"" + name + "\"", Notification.Type.TRAY_NOTIFICATION) ;

            note.show(UI.getCurrent().getPage());
        } catch (DatabaseException ex) {
            window.displayErrorMessage(ex.getMessage());
        }
    }
}
