package dms.control;

import com.vaadin.ui.Button;
import dms.view.EditDocumentWindow;
import dms.view.SelectUserWindow;

/**
 * Listenes on the LoginButton
 * @author Dominik Scholz
 * @version 0.1
 */
public class SelectUserListener implements Button.ClickListener {
    
    private SelectUserWindow selectUserWindow;
    private EditDocumentWindow editDocumentWindow;
    
    public SelectUserListener(EditDocumentWindow editDocumentWindow) {
        this.editDocumentWindow = editDocumentWindow;
    }
    
    @Override
    public void buttonClick(Button.ClickEvent event) {
        editDocumentWindow.addUsers(selectUserWindow.getSelectedUser());
    }

    public void setSelectUserWindow(SelectUserWindow selectUserWindow) {
        this.selectUserWindow = selectUserWindow;
    }
}
