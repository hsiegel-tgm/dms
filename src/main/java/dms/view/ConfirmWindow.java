package dms.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 * @author Dominik
 */
public class ConfirmWindow extends Window {
    
    private final FormLayout form;
    private Label textField;

    public ConfirmWindow(String title, String text, ClickListener listener) {
        super(title);
        
        form = new FormLayout();
        
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("400px");
//        addStyleName("edit-dashboard");
        addStyleName("new-event");
        
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(buildForm(text));
        layout.addComponent(buildMenu(listener));
        
        setContent(layout);
    }
    
    private Component buildForm(String text) {
        form.setSizeUndefined();
        form.setMargin(true);
        form.setSpacing(true);
        
        textField = new Label(text);
        form.addComponent(textField);
        return form;
    }

    private Component buildMenu(ClickListener listener) {
        HorizontalLayout menu = new HorizontalLayout();
        menu.setMargin(true);
        menu.setSpacing(true);
        menu.addStyleName("footer");
        menu.setWidth("100%");
        
        Button ok = new Button("Oui");
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

        Button cancel = new Button("Non");
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
}
    