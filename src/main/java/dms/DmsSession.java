package dms;

import dms.control.DataManager;
import dms.control.DocumentManager;
import dms.control.UserManager;
import dms.model.User;

/**
 * A Session Representaion
 * @author Dominik Scholz
 * @version 0.1
 */
public class DmsSession {
    
    private final DataManager dataManager;
    private final UserManager userManager;
    private final DocumentManager documentManager;
    private User user;
    
    public DmsSession() {
        dataManager = DataManager.get();
        userManager = new UserManager(this);
        documentManager = new DocumentManager(this);
    }
    
    public DataManager getDataManager() { return dataManager; }
    public UserManager getUserManager() { return userManager; }
    public DocumentManager getDocumentManager() { return documentManager; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
