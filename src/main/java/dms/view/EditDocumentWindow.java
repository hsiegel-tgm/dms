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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import dms.model.Document;
import dms.model.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
    private Button addEvent;
    private Button addUser;
    private static final int MAX_DATES = 15;
    private static final int DEFAULT_DATEFIELD_AMOUNT = 3;
    private Label error;
    private boolean prefill;
    private Document tempDocument;
    private Set<User> member;
    private Layout memberContainer;

    public EditDocumentWindow() {
        super("New Event");
        
        form = new FormLayout();
        memberContainer = new CssLayout();
        dateFields = new ArrayList<DateField>();
        member = new TreeSet<User>();
        
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
        super("Edit Document - " + document.getName());
        tempDocument = document;
        
        form = new FormLayout();
        memberContainer = new CssLayout();
        dateFields = new ArrayList<DateField>();
        member = new HashSet<User>();
        
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
        
        name = new TextField("Event Name");
        name.focus();
        name.selectAll();
        name.setStyleName("event-input");
        if(prefill) name.setValue(document.getName());
        form.addComponent(name);
        
        description = new TextArea("Description");
        description.setStyleName("event-input");
        if(prefill) description.setValue(document.getPath());
        form.addComponent(description);
        
        allowMultipleVotes = new CheckBox("Allow Multiple Votes", true);
//        if(prefill) allowMultipleVotes.setValue(document.);
//        form.addComponent(allowMultipleVotes);
//        
//        //Select Dates
//        dateFieldLayout = new GridLayout(3,5);
//        dateFieldLayout.setSpacing(true);
//        dateFieldLayout.setCaption("Dates");
//        int count = 0;
//        if(prefill) {
//            for(VoteDate voteDate : event.getVoteDates()) {
//                count++;
//                DateField dateField = buildDateField();
//                dateField.setValue(voteDate.getDate());
//                dateFieldLayout.addComponent(new DateComponent(dateField));
//            }
//        }
//        while(dateFieldLayout.getComponentCount() < DEFAULT_DATEFIELD_AMOUNT) dateFieldLayout.addComponent(new DateComponent(buildDateField()));
//        form.addComponent(dateFieldLayout);
//        
//        addEvent = new Button("+");
//        addEvent.addClickListener(new ClickListener() {
//            @Override
//            public void buttonClick(ClickEvent event) {
//                dateFieldLayout.replaceComponent(addEvent, new DateComponent(buildDateField()));
//                if(dateFieldLayout.getComponentCount() < MAX_DATES) dateFieldLayout.addComponent(addEvent);
//            }
//        });
//        addEvent.addStyleName("add-button");
//        dateFieldLayout.addComponent(addEvent);
//        
//        //Select Member
//        memberContainer.setCaption("Member");
//        memberContainer.setWidth("480px");
//        if(prefill) {
//            member.addAll(event.getMember());
//            for(User user : member) {
//                memberContainer.addComponent(new UserComponent(user));
//            }
//        }
//        form.addComponent(memberContainer);
//        
//        addUser = new Button("+");
//        addUser.addClickListener(new ClickListener() {
//            @Override
//            public void buttonClick(ClickEvent event) {
//                SelectUserListener listener = new SelectUserListener(EditDocumentWindow.this);
//                SelectUserWindow w = new SelectUserWindow(listener);
//                listener.setSelectUserWindow(w);
//                UI.getCurrent().addWindow(w);
//            }
//        });
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
//        ok.addClickListener(new CreateEventListener(this));
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

    private DateField buildDateField() {
        DateField dateField = new DateField();
        dateField.setDateFormat("dd.MM.yyyy");
        dateField.setLocale(Locale.UK);
        dateField.setStyleName("reindeer");
//        dateField.setResolution(Resolution.MINUTE);
        dateField.setWidth("100px");
        dateFields.add(dateField);
        return dateField;
    }

    public void addMember(Set<User> selectedUser) {
        member.addAll(selectedUser);
        refillMember();
    }

    private class DateComponent extends HorizontalLayout {
        public DateComponent(final DateField dateField) {
            setSpacing(true);
            setStyleName("datefield-component");
            addComponent(dateField);
            Button deleteField = new Button("×");
            deleteField.setPrimaryStyleName("delete-datefield-button");
            deleteField.addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    dateFields.remove(dateField);
                    refillFieldLayout();
                }
            });
            addComponent(deleteField);
        }
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
                    member.remove(user);
                    memberContainer.removeComponent(UserComponent.this);
                }
            });
            addComponent(deleteField);
        }
    }
    
    private void refillFieldLayout() {
        dateFieldLayout.removeAllComponents();
        for(DateField dateField : dateFields) dateFieldLayout.addComponent(new DateComponent(dateField));
        if(dateFieldLayout.getComponentCount() < MAX_DATES) dateFieldLayout.addComponent(addEvent);
    }
    
    private void refillMember() {
        memberContainer.removeAllComponents();
        for(User user : member) memberContainer.addComponent(new UserComponent(user));
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
    
    public String getEventName() { return name.getValue(); }
    public String getEventDescription() { return description.getValue(); }
    public boolean getAllowMultipleVotes() { return allowMultipleVotes.getValue(); }
    public List<DateField> getDateFields() {  return dateFields; }
    public boolean getPrefill() { return prefill; }
    public Set<User> getMember() { return member; }
}
    