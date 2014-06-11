package dms.control;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import dms.DmsUI;
import dms.exceptions.DatabaseException;
import dms.view.LoginView;

/**
 * Listenes on the LoginButton
 * @author Dominik Scholz
 * @version 0.1
 */
public class LoginListener implements Button.ClickListener {
    
    private final LoginView loginView;
    
    public LoginListener(LoginView loginView) {
        this.loginView = loginView;
    }
    
    @Override
    public void buttonClick(Button.ClickEvent event) {
        
        String username = loginView.getUsername();
        byte[] password = loginView.getPassword();
        
        try {
            UserManager um = ((DmsUI)UI.getCurrent()).getDmsSession().getUserManager();
            um.loginUser(username, password);
            event.getButton().removeClickShortcut();
            ((DmsUI) loginView.getUI()).displayMain();
        } catch (DatabaseException ex) {
            loginView.displayErrorMessage(ex.getMessage());
//            if(ex.getArgument().equals("register")) loginView.getUI().setContent(new RegisterView());
        }
    }
}
