package dms.control;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.view.EditDocumentWindow;


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
        
//        
//        List<DateField> dateFields = window.getDateFields();
//        List<Date> dates = new ArrayList<Date>();
//        Set<User> member = window.getMember();
//        Date date;
//        for(DateField dateField : dateFields) {
//            date = dateField.getValue();
//            if(date != null) dates.add(dateField.getValue());            if(window.getPrefill()) dm.createChild(name, description, documentType, window.getVersion(), path);

//        }
//        
//        if(dates.isEmpty()) {
//            window.displayErrorMessage("You have to specify at least one date!");
//            return;
//        }
        
        try {
            DocumentManager dm = ((DmsUI)UI.getCurrent()).getDmsSession().getDocumentManager();

            dm.create(name, description, documentType, window.getVersion(), path,null,null,null);

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
