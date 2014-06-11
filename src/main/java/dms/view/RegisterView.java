package dms.view;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import dms.DmsUI;
import dms.control.RegisterListener;
import java.nio.charset.Charset;

/**
 *
 * @author Dominik
 */
public class RegisterView extends VerticalLayout implements View {
    
    private final TextField username;
    private final PasswordField password;
    private final PasswordField passwordVerification;
    private final TextField email;
    private final CssLayout registerPanel;
    
    private Label error;
    
    public RegisterView() {
        super();
        
        Page.getCurrent().setUriFragment("",false);
        
        setSizeFull();
        addStyleName("login-layout");

        registerPanel = new CssLayout();
        registerPanel.addStyleName("login-panel");

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        registerPanel.addComponent(labels);
        
        Resource res = new ThemeResource("img/dms.png");

        Image image = new Image(null, res);
        image.setHeight("200px");
        labels.addComponent(image);
        labels.setComponentAlignment(image, Alignment.TOP_CENTER);
        
        VerticalLayout fields = new VerticalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        username = new TextField("Nom d#utilisateur");
        username.focus();
        fields.addComponent(username);
        fields.setComponentAlignment(username, Alignment.MIDDLE_CENTER);

        password = new PasswordField("Mot de passe");
        fields.addComponent(password);
        fields.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        
        passwordVerification = new PasswordField("Répéter mot de passe");
        fields.addComponent(passwordVerification);
        fields.setComponentAlignment(passwordVerification, Alignment.MIDDLE_CENTER);
        
        email = new TextField("Courriel (optionale):");
        fields.addComponent(email);
        fields.setComponentAlignment(email, Alignment.MIDDLE_CENTER);
        
        HorizontalLayout nav = new HorizontalLayout();
        nav.setSpacing(true);
        
        final Button back = new Button("Retour");
        back.setId("back");
        back.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((DmsUI) UI.getCurrent()).displayLogin();
            }
        });
        nav.addComponent(back);

        final Button signin = new Button("Enregistrer");
        signin.setId("signin");
        signin.addStyleName("default");
        signin.setClickShortcut(KeyCode.ENTER, null);
        nav.addComponent(signin);
        
        fields.addComponent(nav);
        fields.setComponentAlignment(nav, Alignment.BOTTOM_CENTER);

        signin.addClickListener(new RegisterListener(this));
        
        registerPanel.addComponent(fields);

        addComponent(registerPanel);
        setComponentAlignment(registerPanel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) { 
    }

    public String getUsername() { return username.getValue(); }
    public byte[] getPassword() { return password.getValue().getBytes(Charset.forName("UTF-8")); }
    public byte[] getPasswordVerification() { return passwordVerification.getValue().getBytes(Charset.forName("UTF-8")); }
    public String getEmail() { return email.getValue(); }

    public void displayErrorMessage(String message) {
        //Remove old error message
        if(error != null) registerPanel.removeComponent(error);

        //Add new error message
        error = new Label(message,ContentMode.HTML);
        error.addStyleName("error");
        error.setSizeUndefined();
        error.addStyleName("light");

        //Add animation
        error.addStyleName("v-animate-reveal");
        registerPanel.addComponent(error);
        username.focus();
    }
    
}
