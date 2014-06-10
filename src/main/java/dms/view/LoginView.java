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
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import dms.DmsUI;
import dms.control.LoginListener;
import java.nio.charset.Charset;

/**
 *
 * @author Dominikimport sigmascheduler.userinterface.views.RegisterView;

 */
public class LoginView extends VerticalLayout implements View {
    
    private final TextField username;
    private final PasswordField password;
    private final CssLayout loginPanel;
    
    private Component error;
    
    public LoginView() {
        super();
        
        Page.getCurrent().setUriFragment("",false);
        
        setSizeFull();
        addStyleName("login-layout");

        loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);
        
        Resource res = new ThemeResource("img/dms.png");

        Image image = new Image(null, res);
        image.setHeight("200px");
        labels.addComponent(image);
        labels.setComponentAlignment(image, Alignment.TOP_CENTER);

        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        username = new TextField("Username");
        username.focus();
        fields.addComponent(username);

        password = new PasswordField("Password");
        fields.addComponent(password);

        final Button signin = new Button("Sign In");
        signin.setId("signin");
        signin.addStyleName("default");
        signin.setClickShortcut(KeyCode.ENTER, null);
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        signin.addClickListener(new LoginListener(this));
        
        loginPanel.addComponent(fields);
        
        final Button signup = new Button("Not registerd yet?, Sign up!");
        signup.setId("signup");
        signup.setPrimaryStyleName("link");
        signup.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ((DmsUI) UI.getCurrent()).setContent(new RegisterView());
            }
        });
        loginPanel.addComponent(signup);

        addComponent(loginPanel);
        setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) { 
    }

    public String getUsername() { return username.getValue(); }
    public byte[] getPassword() { return password.getValue().getBytes(Charset.forName("UTF-8")); }

    public void displayErrorMessage(String message) {
        //Remove old error message
        if (error != null) loginPanel.removeComponent(error);

        //Add new error message
        error = new Label(message,ContentMode.HTML);
        error.addStyleName("error");
        error.setSizeUndefined();
        error.addStyleName("light");

        //Add animation
        error.addStyleName("v-animate-reveal");
        loginPanel.addComponent(error);
        username.focus();
    }
    
}
