package dms.control;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.view.RegisterView;
import java.util.Arrays;
import org.jboss.logging.Logger;

/**
 * Listenes on the user select
 * @author Dominik Scholz
 * @version 0.1
 */
public class RegisterListener implements Button.ClickListener {
    
    private RegisterView registerView;
    
    public RegisterListener(RegisterView registerView) {
        this.registerView = registerView;
    }
    
    @Override
    public void buttonClick(Button.ClickEvent event) {
        
        String username = registerView.getUsername();
        byte[] password = registerView.getPassword();
        byte[] passwordVerification = registerView.getPasswordVerification();
        String email = registerView.getEmail();
        
        if(!Arrays.equals(password, passwordVerification)) {
            registerView.displayErrorMessage("The two passwordfields doesn't match!");
            return;
        }
        
        try {
            UserManager um = ((DmsUI)UI.getCurrent()).getDmsSession().getUserManager();
            um.registerUser(username,email,password);
            event.getButton().removeClickShortcut();
            ((DmsUI) registerView.getUI()).displayLogin();
            Logger.getLogger(username).log(Logger.Level.INFO,"Registered User \"" + username + "\"");
        } catch (DatabaseException ex) {
            registerView.displayErrorMessage(ex.getMessage());
        }
    }
}
