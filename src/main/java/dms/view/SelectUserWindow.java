package dms.view;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dominik
 */
public class SelectUserWindow extends Window {
    
    private final FormLayout form;
    private Label textField;
    private Table table;

    public SelectUserWindow(ClickListener listener) {
        super("User Selection");
        
        form = new FormLayout();
        
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("280px");
//        addStyleName("edit-dashboard");
        addStyleName("new-event");
        
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(buildForm());
        layout.addComponent(buildMenu(listener));
        
        setContent(layout);
    }
    
    private Component buildForm() {
        form.setSizeUndefined();
        form.setMargin(true);
        form.setSpacing(true);
        
        table = new Table();
        List<User> users;
        try {
            users = ((DmsUI) UI.getCurrent()).getDmsSession().getUserManager().getAllUsers();
        } catch (DatabaseException ex) {
            users = new ArrayList<User>();
        }
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getUsername().compareTo(user2.getUsername());
            }
        });
        IndexedContainer ic = new IndexedContainer();
        ic.addContainerProperty("User", String.class, null);
        for(User user : users) {
            ic.addItem(user);
            ic.getContainerProperty(user, "User").setValue(user.getUsername());
        }
        table.setMultiSelect(true);
        table.setContainerDataSource(ic);
        table.setVisibleColumns("User");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setHeight("400px");
        table.setWidth("240px");
        form.addComponent(table);
        return form;
    }

    private Component buildMenu(ClickListener listener) {
        HorizontalLayout menu = new HorizontalLayout();
        menu.setMargin(true);
        menu.setSpacing(true);
        menu.addStyleName("footer");
        menu.setWidth("100%");
        
        Button ok = new Button("Add");
        ok.addStyleName("wide");
        ok.addStyleName("default");
        ok.addClickListener(listener);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        ok.setClickShortcut(KeyCode.ENTER, null);
        menu.addComponent(ok);
        menu.setExpandRatio(ok, 1);
        menu.setComponentAlignment(ok, Alignment.TOP_RIGHT);

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }
        });
        cancel.setClickShortcut(KeyCode.ESCAPE, null);
        menu.addComponent(cancel);
        
        return menu;
    }
    
    public Set<User> getSelectedUser() {
        return (Set<User>) table.getValue();
    }
}
    