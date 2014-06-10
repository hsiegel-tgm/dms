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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import dms.control.CreateDocumentListener;
import dms.control.CreateKeyWordListener;
import dms.control.FileUploader;
import dms.control.SelectCategoryListener;
import dms.control.SelectUserListener;
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
public class EditDocumentWindow extends Window {
    
    private final FormLayout form;
    private TextField name;
    private TextArea description;
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
    
    public EditDocumentWindow() {
        super("nouveaux document");
        
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
        layout.addComponent(buildForm());
        layout.addComponent(buildMenu());
        
        setContent(layout);
    }
    
    public EditDocumentWindow(Document document) {
        super("modifier document - " + document.getName());
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
        
        name = new TextField("nom du document");
        name.focus();
        name.selectAll();
        name.setStyleName("event-input");
        if(prefill) name.setValue(document.getName());
        form.addComponent(name);
        
        description = new TextArea("description");
        description.setStyleName("event-input");
        if(prefill) description.setValue(document.getDescription());
        form.addComponent(description);
         version = prefill ? document.getVersion()+1 : 1;
        FileUploader uploader = new FileUploader(this,version);
        file = new Upload(null, uploader);
        file.setImmediate(false);
        file.setButtonCaption("enregistre");
        final UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(file,uploader);
        file.addStartedListener(new StartedListener() {
            @Override
            public void uploadStarted(final StartedEvent event) {
                if (uploadInfoWindow.getParent() == null) {
                    UI.getCurrent().addWindow(uploadInfoWindow);
                }
                uploadInfoWindow.setClosable(false);
            }
        });
        file.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(final FinishedEvent event) {
                uploadInfoWindow.setClosable(true);
                uploaded = true;
            }
        });
        file.addFinishedListener(uploader);
        form.addComponent(file);
        
        //Select Category
        categoryContainer.setCaption("catégorie");
        categoryContainer.setWidth("480px");
        if(prefill) {
            categories.addAll(document.getCategories());
            for(Category category : categories) {
                categoryContainer.addComponent(new CategoryComponent(category));
            }
        }
        form.addComponent(categoryContainer);
        
        addCategory = new Button("+");
        addCategory.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                SelectCategoryListener listener = new SelectCategoryListener(EditDocumentWindow.this);
                SelectCategoryWindow w = new SelectCategoryWindow(listener);
                listener.setSelectCategoryWindow(w);
                UI.getCurrent().addWindow(w);
            }
        });
        addCategory.addStyleName("add-button");
        form.addComponent(addCategory);
        
        //Select KeyWord
        keywordContainer.setCaption("mot de clé");
        keywordContainer.setWidth("480px");
        if(prefill) {
            keywords.addAll(document.getKeyWords());
            for(KeyWord keyword : keywords) {
                keywordContainer.addComponent(new KeywordComponent(keyword));
            }
        }
        form.addComponent(keywordContainer);
        
        addKeyWord = new Button("+");
        addKeyWord.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                CreateKeyWordListener listener = new CreateKeyWordListener(EditDocumentWindow.this);
                CreateKeyWordWindow w = new CreateKeyWordWindow(listener);
                listener.setCreateKeyWordWindow(w);
                UI.getCurrent().addWindow(w);
            }
        });
        addKeyWord.addStyleName("add-button");
        form.addComponent(addKeyWord);
//        
        //Select User
        userContainer.setCaption("utilisateurs");
        userContainer.setWidth("480px");
        if(prefill) {
            users.addAll(document.getUsers());
            for(User user : users) {
                userContainer.addComponent(new UserComponent(user));
            }
        }
        form.addComponent(userContainer);
        
        addUser = new Button("+");
        addUser.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                SelectUserListener listener = new SelectUserListener(EditDocumentWindow.this);
                SelectUserWindow w = new SelectUserWindow(listener);
                listener.setSelectUserWindow(w);
                UI.getCurrent().addWindow(w);
            }
        });
        addUser.addStyleName("add-button");
        form.addComponent(addUser);
        
        
        
        return form;
    }

    private Component buildMenu() {
        HorizontalLayout menu = new HorizontalLayout();
        menu.setMargin(true);
        menu.setSpacing(true);
        menu.addStyleName("footer");
        menu.setWidth("100%"); 
        
        if(prefill) {
            Button publish = new Button("Publish");
            publish.addStyleName("wide");
            publish.addStyleName("default");
//            publish.addClickListener(new CreateEventListener(this));
//            publish.addClickListener(new PublishEventListener(this));
            menu.addComponent(publish);
            menu.setComponentAlignment(publish, Alignment.TOP_LEFT);
        }
        
        Button ok = new Button("Save");
        ok.addStyleName("wide");
        ok.addStyleName("default");
        ok.addClickListener(new CreateDocumentListener(this));
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

    public void addUsers(Set<User> selectedUsers) {
        users.addAll(selectedUsers);
        refillUsers();
    }
    
    public void addCategories(Set<Category> selectedCategories) {
        categories.addAll(selectedCategories);
        refillCategories();
    }
    
    public void addKeywords(Set<KeyWord> selectedKeywords) {
        keywords.addAll(selectedKeywords);
        refillKeyWords();
    }
    
    public void addKeyword(KeyWord keyWord) {
        keywords.add(keyWord);
        refillKeyWords();
    }
    
    private class UserComponent extends HorizontalLayout {
        public UserComponent(final User user) {
            setSpacing(true);
            setStyleName("user-component");
            addComponent(new Label(user.getUsername()));
            Button deleteField = new Button("×");
            deleteField.setPrimaryStyleName("delete-user-button");
            deleteField.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    users.remove(user);
                    userContainer.removeComponent(UserComponent.this);
                }
            });
            addComponent(deleteField);
        }
    }
    
    private class KeywordComponent extends HorizontalLayout {
        public KeywordComponent(final KeyWord keyword) {
            setSpacing(true);
            setStyleName("user-component");
            addComponent(new Label(keyword.getName()));
            Button deleteField = new Button("×");
            deleteField.setPrimaryStyleName("delete-user-button");
            deleteField.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    keywords.remove(keyword);
                    keywordContainer.removeComponent(KeywordComponent.this);
                }
            });
            addComponent(deleteField);
        }
    }
    
    private class CategoryComponent extends HorizontalLayout {
        public CategoryComponent(final Category category) {
            setSpacing(true);
            setStyleName("user-component");
            addComponent(new Label(category.getName()));
            Button deleteField = new Button("×");
            deleteField.setPrimaryStyleName("delete-user-button");
            deleteField.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    categories.remove(category);
                    categoryContainer.removeComponent(CategoryComponent.this);
                }
            });
            addComponent(deleteField);
        }
    }
    
    private void refillUsers() {
        userContainer.removeAllComponents();
        for(User user : users) userContainer.addComponent(new UserComponent(user));
    }
    
    private void refillCategories() {
        categoryContainer.removeAllComponents();
        for(Category category : categories) categoryContainer.addComponent(new CategoryComponent(category));
    }
    
    private void refillKeyWords() {
        keywordContainer.removeAllComponents();
        for(KeyWord keyword : keywords) keywordContainer.addComponent(new KeywordComponent(keyword));
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
    
  
    public boolean getPrefill() { return prefill; }
    public Set<User> getMember() { return users; }

    public TextField getName() {
        return name;
    }

    public TextArea getDocumentDescription() {
        return description;
    }

    public Upload getFile() {
        return file;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public int getVersion() {
        return version;
    }
    public String getPath() {
        return path;
    }
     public String getDocumentType() {
        return documentType;
    }
    
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
    