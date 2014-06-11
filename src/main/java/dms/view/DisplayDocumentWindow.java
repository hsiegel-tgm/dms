package dms.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import dms.model.Category;
import dms.model.Document;
import dms.model.KeyWord;
import dms.model.User;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Dominik
 */
public class DisplayDocumentWindow extends Window {
    
    private final FormLayout form;
    private TextField name;
    private CheckBox allowMultipleVotes;
    private List<DateField> dateFields;
    private GridLayout dateFieldLayout;
    private Button addKeyWord;
    private Button addCategory;
    private Button addUser;
    private static final int MAX_DATES = 15;
    private static final int DEFAULT_DATEFIELD_AMOUNT = 3;
    private Label error;
    private boolean prefill;
    private Document tempDocument;
    private Set<User> users;
    private Set<KeyWord> keywords;
    private Set<Category> categories;
    private Layout userContainer;
    private Layout keywordContainer;
    private Layout categoryContainer;
    private Upload file;
    private boolean uploaded = false;
    private int version;
    private String path, documentType;

    public DisplayDocumentWindow(Document document) {
        super("document - " + document.getName());
        tempDocument = document;
        
        form = new FormLayout();
        userContainer = new CssLayout();
        keywordContainer = new CssLayout();
        categoryContainer = new CssLayout();
        users = new TreeSet<User>();
        keywords = new TreeSet<KeyWord>();
        categories = new TreeSet<Category>();
        
        setModal(true);
        setClosable(false);
        setResizable(false);
//        addStyleName("edit-dashboard");
        addStyleName("new-event");
        
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(buildForm(document));
        layout.addComponent(buildMenu());
        
        setContent(layout);
    }
    
    private Component buildForm() {
        return buildForm(null);
    }
    
    private Component buildForm(Document document) {
        prefill = document != null;
        
        form.setSizeUndefined();
        form.setMargin(true);
        form.setSpacing(true);
        
        Label description = new Label("description");
        description.setStyleName("event-input");
        if(prefill) description.setValue(document.getDescription());
        form.addComponent(description);
        
//        Label description = new Label("description");
//        description.setStyleName("event-input");
//        if(prefill) description.setValue(document.getDescription());
//        form.addComponent(description);
//        
//       
        
        
        return form;
    }

    private Component buildMenu() {
        HorizontalLayout menu = new HorizontalLayout();
        menu.setMargin(true);
        menu.setSpacing(true);
        menu.addStyleName("footer");
        menu.setWidth("100%"); 
        
        Button ok = new Button("Download");
        ok.addStyleName("wide");
        ok.addStyleName("default");
//        ok.addClickListener(new CreateDocumentListener(this));
        ok.setClickShortcut(KeyCode.ENTER, null);
        menu.addComponent(ok);
        menu.setExpandRatio(ok, 1);
        menu.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        
        Button cancel = new Button("Cancel");
        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                //Are you suer you want to delete blablabla...
                close();
            }
        });
        cancel.setClickShortcut(KeyCode.ESCAPE, null);
        menu.addComponent(cancel);
        
        return menu;
    }

    public void displayErrorMessage(String message) {
        //Remove old error message
        if(error != null) form.removeComponent(error);

        //Add new error message
        error = new Label(message,ContentMode.HTML);
        error.addStyleName("error");
        error.setSizeUndefined();
        error.addStyleName("light");

        //Add animation
        error.addStyleName("v-animate-reveal");
        form.addComponent(error);
    }
}
    