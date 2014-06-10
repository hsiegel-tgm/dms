package dms.control;

import com.vaadin.ui.Button;
import dms.model.KeyWord;
import dms.view.CreateKeyWordWindow;
import dms.view.EditDocumentWindow;

/**
 * Listenes on the LoginButton
 * @author Dominik Scholz
 * @version 0.1
 */
public class CreateKeyWordListener implements Button.ClickListener {
    
    private CreateKeyWordWindow createKeyWordWindow;
    private EditDocumentWindow editDocumentWindow;
    
    public CreateKeyWordListener(EditDocumentWindow editDocumentWindow) {
        this.editDocumentWindow = editDocumentWindow;
    }
    
    @Override
    public void buttonClick(Button.ClickEvent event) {
        editDocumentWindow.addKeyword(new KeyWord(createKeyWordWindow.getKeyWord()));
    }

    public void setCreateKeyWordWindow(CreateKeyWordWindow createKeyWordWindow) {
        this.createKeyWordWindow = createKeyWordWindow;
    }
}
